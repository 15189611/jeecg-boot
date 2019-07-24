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
          label="contactsId"
          hasFeedback>
          <a-input placeholder="请输入contactsId" v-decorator="['contactsId', validatorRules.contactsId ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="chanceId"
          hasFeedback>
          <a-input placeholder="请输入chanceId" v-decorator="['chanceId', validatorRules.chanceId ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="connTime"
          hasFeedback>
          <a-date-picker showTime format="YYYY-MM-DD HH:mm:ss" v-decorator="[ 'connTime', validatorRules.connTime ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="沟通阶段"
          hasFeedback>
          <a-input-number v-decorator="[ 'salestage', validatorRules.salestage ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="销售方式"
          hasFeedback>
          <a-input-number v-decorator="[ 'salemode', validatorRules.salemode ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="主题名称"
          hasFeedback>
          <a-input placeholder="请输入主题名称" v-decorator="['name', validatorRules.name ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="intro"
          hasFeedback>
          <a-input placeholder="请输入intro" v-decorator="['intro', validatorRules.intro ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="status"
          hasFeedback>
          <a-input placeholder="请输入status" v-decorator="['status', validatorRules.status ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="下次联系时间"
          hasFeedback>
          <a-date-picker showTime format="YYYY-MM-DD HH:mm:ss" v-decorator="[ 'nextTime', validatorRules.nextTime ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="下次沟通主题"
          hasFeedback>
          <a-input placeholder="请输入下次沟通主题" v-decorator="['nexttitle', validatorRules.nexttitle ]" />
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
    name: 'TraceModal',
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
          contactsId: { rules: [{ required: true, message: '请输入contactsId!' }] },
          chanceId: { rules: [{ required: true, message: '请输入chanceId!' }] },
          connTime: { rules: [{ required: true, message: '请输入connTime!' }] },
          salestage: { rules: [{ required: true, message: '请输入沟通阶段!' }] },
          salemode: { rules: [{ required: true, message: '请输入销售方式!' }] },
          name: { rules: [{ required: true, message: '请输入主题名称!' }] },
          intro: { rules: [{ required: true, message: '请输入intro!' }] },
          status: { rules: [{ required: true, message: '请输入status!' }] },
          nextTime: { rules: [{ required: true, message: '请输入下次联系时间!' }] },
          nexttitle: { rules: [{ required: true, message: '请输入下次沟通主题!' }] },
        },
        url: {
          add: '/crm/customer/addTrace',
          edit: '/crm/customer/editTrace'
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
          this.form.setFieldsValue(pick(this.model, 'customerId', 'contactsId', 'chanceId', 'connTime', 'salestage', 'salemode', 'name', 'intro', 'status', 'nextTime', 'nexttitle', ))
          // 时间格式化
          this.form.setFieldsValue({ connTime: this.model.connTime ? moment(this.model.connTime) : null })
          this.form.setFieldsValue({ nextTime: this.model.nextTime ? moment(this.model.nextTime) : null })
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
            formData.connTime = formData.connTime?formData.connTime.format('YYYY-MM-DD HH:mm:ss'):null;
            formData.nextTime = formData.nextTime?formData.nextTime.format('YYYY-MM-DD HH:mm:ss'):null;
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
