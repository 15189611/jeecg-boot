<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :okButtonProps="{ props: {disabled: disableSubmit} }"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="customerId"
          :hidden="hiding"
          hasFeedback>
          <a-input placeholder="请输入customerId" v-decorator="['customerId', {'initialValue':this.mainId}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="name"
          hasFeedback>
          <a-input placeholder="请输入name" v-decorator="['name', validatorRules.name ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="姓别"
          hasFeedback>
          <a-input placeholder="请输入姓别" v-decorator="['gender', validatorRules.gender ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="职位、"
          hasFeedback>
          <a-input placeholder="请输入职位、" v-decorator="['postion', validatorRules.postion ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="tel"
          hasFeedback>
          <a-input placeholder="请输入tel" v-decorator="['tel', validatorRules.tel ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="mobile"
          hasFeedback>
          <a-input placeholder="请输入mobile" v-decorator="['mobile', validatorRules.mobile ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="qicq"
          hasFeedback>
          <a-input placeholder="请输入qicq" v-decorator="['qicq', validatorRules.qicq ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="email"
          hasFeedback>
          <a-input placeholder="请输入email" v-decorator="['email', validatorRules.email ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="zipcode"
          hasFeedback>
          <a-input placeholder="请输入zipcode" v-decorator="['zipcode', validatorRules.zipcode ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="address"
          hasFeedback>
          <a-input placeholder="请输入address" v-decorator="['address', validatorRules.address ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="intro"
          hasFeedback>
          <a-input placeholder="请输入intro" v-decorator="['intro', validatorRules.intro ]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import {httpAction} from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from 'moment'
  import JDate from '@/components/jeecg/JDate'

  export default {
    components: {
      JDate
    },
    name: 'ContactsModal',
    data() {
      return {
        title: '操作',
        visible: false,
        model: {},
        labelCol: {
          xs: {span: 24},
          sm: {span: 5}
        },
        wrapperCol: {
          xs: {span: 24},
          sm: {span: 16}
        },
        moment,
        format: 'YYYY-MM-DD HH:mm:ss',
        disableSubmit: false,
        mainId: '',
        hiding: false,
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules: {
          name: { rules: [{ required: true, message: '请输入name!' }] },
          gender: { rules: [{ required: true, message: '请输入姓别!' }] },
          postion: { rules: [{ required: true, message: '请输入职位、!' }] },
          tel: { rules: [{ required: true, message: '请输入tel!' }] },
          mobile: { rules: [{ required: true, message: '请输入mobile!' }] },
          qicq: { rules: [{ required: true, message: '请输入qicq!' }] },
          email: { rules: [{ required: true, message: '请输入email!' }] },
          zipcode: { rules: [{ required: true, message: '请输入zipcode!' }] },
          address: { rules: [{ required: true, message: '请输入address!' }] },
          intro: { rules: [{ required: true, message: '请输入intro!' }] },
        },
        url: {
          add: '/crm/customer/addContacts',
          edit: '/crm/customer/editContacts'
        }
      }
    },
    created() {
    },
    methods: {
      add(mainId) {
        if (mainId) {
          this.edit({mainId}, '')
        } else {
          this.$message.warning('请选择一条数据')
        }
      },
      detail(record) {
        this.edit(record, 'd')
      },
      edit(record, v) {
        if (v == 'e') {
          this.hiding = false;
          this.disableSubmit = false;
        } else if (v == 'd') {
          this.hiding = false;
          this.disableSubmit = true;
        } else {
          this.hiding = true;
          this.disableSubmit = false;
        }
        this.form.resetFields();
        this.mainId = record.mainId;
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'customerId', 'name', 'gender', 'postion', 'tel', 'mobile', 'qicq', 'email', 'zipcode', 'address', 'intro', ))
          // 时间格式化
        })
      },
      close() {
        this.$emit('close');
        this.visible = false;
      },
      handleOk() {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if (!this.model.id) {
              httpurl += this.url.add;
              method = 'post';
            } else {
              httpurl += this.url.edit;
              method = 'put';
            }
            let formData = Object.assign(this.model, values);
            //时间格式化
            httpAction(httpurl, formData, method).then((res) => {
              if (res.success) {
                that.$message.success(res.message);
                that.$emit('ok');
              } else {
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
        })
      },
      handleCancel() {
        this.close();
      }
    }
  }
</script>

<style scoped>

</style>
