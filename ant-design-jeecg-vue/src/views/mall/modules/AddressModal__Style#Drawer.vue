<template>
  <a-drawer
      :title="title"
      :width="800"
      placement="right"
      :closable="false"
      @close="close"
      :visible="visible"
  >

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
      
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="用户ID">
          <a-input placeholder="请输入用户ID" v-decorator="['userId', validatorRules.userId ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="默认收获地址: 0/非默认,1/默认">
          <a-input-number v-decorator="[ 'isDefault', validatorRules.isDefault ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="收货人">
          <a-input placeholder="请输入收货人" v-decorator="['consignee', validatorRules.consignee ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="收货人手机">
          <a-input placeholder="请输入收货人手机" v-decorator="['mobile', validatorRules.mobile ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="邮编">
          <a-input placeholder="请输入邮编" v-decorator="['zipCode', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="省份">
          <a-input placeholder="请输入省份" v-decorator="['provinceName', validatorRules.provinceName ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="地市">
          <a-input placeholder="请输入地市" v-decorator="['cityName', validatorRules.cityName ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="县区">
          <a-input placeholder="请输入县区" v-decorator="['districtName', validatorRules.districtName ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="详细地址">
          <a-input placeholder="请输入详细地址" v-decorator="['address', validatorRules.address ]" />
        </a-form-item>
		
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "AddressModal",
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        userId:{rules: [{ required: true, message: '请输入用户ID!' }]},
        isDefault:{rules: [{ required: true, message: '请输入默认收获地址: 0/非默认,1/默认!' }]},
        consignee:{rules: [{ required: true, message: '请输入收货人!' }]},
        mobile:{rules: [{ required: true, message: '请输入收货人手机!' }]},
        provinceName:{rules: [{ required: true, message: '请输入省份!' }]},
        cityName:{rules: [{ required: true, message: '请输入地市!' }]},
        districtName:{rules: [{ required: true, message: '请输入县区!' }]},
        address:{rules: [{ required: true, message: '请输入详细地址!' }]},
        },
        url: {
          add: "/mall/address/add",
          edit: "/mall/address/edit",
        },
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'userId','isDefault','consignee','mobile','zipCode','provinceName','cityName','districtName','address'))
		  //时间格式化
        });

      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            //时间格式化
            
            console.log(formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })



          }
        })
      },
      handleCancel () {
        this.close()
      },


    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>