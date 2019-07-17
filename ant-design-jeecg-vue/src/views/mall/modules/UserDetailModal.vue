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
          label="用户ID"
          :hidden="hiding"
          hasFeedback>
          <a-input placeholder="请输入用户ID" v-decorator="['userId', {'initialValue':this.mainId}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="性别 0/男,1/女"
          hasFeedback>
          <a-input-number v-decorator="[ 'gender', validatorRules.gender ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="姓名"
          hasFeedback>
          <a-input placeholder="请输入姓名" v-decorator="['name', validatorRules.name ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="手机"
          hasFeedback>
          <a-input placeholder="请输入手机" v-decorator="['mobile', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="邮箱"
          hasFeedback>
          <a-input placeholder="请输入邮箱" v-decorator="['email', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="生日"
          hasFeedback>
          <a-date-picker showTime format="YYYY-MM-DD HH:mm:ss" v-decorator="[ 'birthdate', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="身份证号"
          hasFeedback>
          <a-input placeholder="请输入身份证号" v-decorator="['idCard', validatorRules.idCard ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="地址"
          hasFeedback>
          <a-input placeholder="请输入地址" v-decorator="['address', validatorRules.address ]" />
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
    name: 'UserDetailModal',
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
          gender: { rules: [{ required: true, message: '请输入性别 0/男,1/女!' }] },
          name: { rules: [{ required: true, message: '请输入姓名!' }] },
          idCard: { rules: [{ required: true, message: '请输入身份证号!' }] },
          address: { rules: [{ required: true, message: '请输入地址!' }] },
        },
        url: {
          add: '/mall/userAccount/addUserDetail',
          edit: '/mall/userAccount/editUserDetail'
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
          this.form.setFieldsValue(pick(this.model, 'userId', 'gender', 'name', 'mobile', 'email', 'birthdate', 'idCard', 'address', ))
          // 时间格式化
          this.form.setFieldsValue({ birthdate: this.model.birthdate ? moment(this.model.birthdate) : null })
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
            formData.birthdate = formData.birthdate?formData.birthdate.format('YYYY-MM-DD HH:mm:ss'):null;
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
