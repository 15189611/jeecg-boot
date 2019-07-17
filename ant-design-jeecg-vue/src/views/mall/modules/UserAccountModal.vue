<template>
  <a-modal
    :title="title"
    :width="1000"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <!-- 主表单区域 -->
      <a-form :form="form">
		        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="用户名">
          <a-input placeholder="请输入用户名" v-decorator="['userName', validatorRules.userName ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="昵称">
          <a-input placeholder="请输入昵称" v-decorator="['nickName', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="头像">
          <a-input placeholder="请输入头像" v-decorator="['avatar', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="是否锁定: 0/锁定,1/正常">
          <a-input-number v-decorator="[ 'locked', validatorRules.locked ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="最后登录时间">
          <a-date-picker showTime format="YYYY-MM-DD HH:mm:ss" v-decorator="[ 'lastVisitTime', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="注册时间">
          <a-date-picker showTime format="YYYY-MM-DD HH:mm:ss" v-decorator="[ 'registerTime', {}]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import {httpAction} from '@/api/manage'
  import JDate from '@/components/jeecg/JDate'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: 'UserAccountModal',
    components: {
      JDate
    },
    data() {
      return {
        title: "操作",
        visible: false,
        model: {},
        labelCol: {
          xs: {span: 24},
          sm: {span: 5},
        },
        wrapperCol: {
          xs: {span: 24},
          sm: {span: 16},
        },
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules: {
          userName: { rules: [{ required: true, message: '请输入用户名!' }] },
          locked: { rules: [{ required: true, message: '请输入是否锁定: 0/锁定,1/正常!' }] },
        },
        url: {
          add: "/mall/userAccount/add",
          edit: "/mall/userAccount/edit",
          userAuthList: '/mall/userAccount/queryUserAuthByMainId',
          userDetailList: '/mall/userAccount/queryUserDetailByMainId',
        }
      }
    },
    methods: {
      add() {
        this.edit({});
      },
      edit(record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        //初始化明细表数据
        console.log(this.model.id)
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'userName', 'nickName', 'avatar', 'locked', 'lastVisitTime', 'registerTime', ))
          // 时间格式化
          this.form.setFieldsValue({ lastVisitTime: this.model.lastVisitTime ? moment(this.model.lastVisitTime) : null })
          this.form.setFieldsValue({ registerTime: this.model.registerTime ? moment(this.model.registerTime) : null })
        });
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
            formData.lastVisitTime = formData.lastVisitTime?formData.lastVisitTime.format('YYYY-MM-DD HH:mm:ss'):null;
            formData.registerTime = formData.registerTime?formData.registerTime.format('YYYY-MM-DD HH:mm:ss'):null;
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
        this.close()
      }
    }
  }
</script>

<style scoped>
  .ant-btn {
    padding: 0 10px;
    margin-left: 3px;
  }

  .ant-form-item-control {
    line-height: 0px;
  }

  /** 主表单行间距 */
  .ant-form .ant-form-item {
    margin-bottom: 10px;
  }

  /** Tab页面行间距 */
  .ant-tabs-content .ant-form-item {
    margin-bottom: 0px;
  }
</style>