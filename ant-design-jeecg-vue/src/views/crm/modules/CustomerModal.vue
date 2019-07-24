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
          label="客户名称">
          <a-input placeholder="请输入客户名称" v-decorator="['name', validatorRules.name ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="客户编号">
          <a-input placeholder="请输入客户编号" v-decorator="['customerNo', validatorRules.customerNo ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="所在地区">
          <a-input-number v-decorator="[ 'areaId', validatorRules.areaId ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="客户来源">
          <a-input-number v-decorator="[ 'source', validatorRules.source ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="经济类型">
          <a-input-number v-decorator="[ 'ecotype', validatorRules.ecotype ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="客户等级">
          <a-input-number v-decorator="[ 'level', validatorRules.level ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="客户行业">
          <a-input-number v-decorator="[ 'trade', validatorRules.trade ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="满意度（1-5），默认为3">
          <a-input placeholder="请输入满意度（1-5），默认为3" v-decorator="['satisfy', validatorRules.satisfy ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="信用度（1-5），默认为3">
          <a-input placeholder="请输入信用度（1-5），默认为3" v-decorator="['credit', validatorRules.credit ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="联系地址">
          <a-input placeholder="请输入联系地址" v-decorator="['address', validatorRules.address ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="网址">
          <a-input placeholder="请输入网址" v-decorator="['website', validatorRules.website ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="邮编">
          <a-input placeholder="请输入邮编" v-decorator="['zipcode', validatorRules.zipcode ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="联系人">
          <a-input placeholder="请输入联系人" v-decorator="['linkman', validatorRules.linkman ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="手机">
          <a-input placeholder="请输入手机" v-decorator="['mobile', validatorRules.mobile ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="座机">
          <a-input placeholder="请输入座机" v-decorator="['tel', validatorRules.tel ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="传真">
          <a-input placeholder="请输入传真" v-decorator="['fax', validatorRules.fax ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="邮箱">
          <a-input placeholder="请输入邮箱" v-decorator="['email', validatorRules.email ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="主营产品">
          <a-input placeholder="请输入主营产品" v-decorator="['mainProduct', validatorRules.mainProduct ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="沟通情况">
          <a-input placeholder="请输入沟通情况" v-decorator="['talk', validatorRules.talk ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="status">
          <a-input placeholder="请输入status" v-decorator="['status', validatorRules.status ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="介绍">
          <a-input placeholder="请输入介绍" v-decorator="['intro', validatorRules.intro ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="归属人员">
          <a-input placeholder="请输入归属人员" v-decorator="['ownerUserId', validatorRules.ownerUserId ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="下次沟通时间">
          <a-date-picker showTime format="YYYY-MM-DD HH:mm:ss" v-decorator="[ 'nextTime', validatorRules.nextTime ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="最近联系时间">
          <a-date-picker showTime format="YYYY-MM-DD HH:mm:ss" v-decorator="[ 'connTime', validatorRules.connTime ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="最近沟通内容">
          <a-input placeholder="请输入最近沟通内容" v-decorator="['connBody', validatorRules.connBody ]" />
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
    name: 'CustomerModal',
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
          name: { rules: [{ required: true, message: '请输入客户名称!' }] },
          customerNo: { rules: [{ required: true, message: '请输入客户编号!' }] },
          areaId: { rules: [{ required: true, message: '请输入所在地区!' }] },
          source: { rules: [{ required: true, message: '请输入客户来源!' }] },
          ecotype: { rules: [{ required: true, message: '请输入经济类型!' }] },
          level: { rules: [{ required: true, message: '请输入客户等级!' }] },
          trade: { rules: [{ required: true, message: '请输入客户行业!' }] },
          satisfy: { rules: [{ required: true, message: '请输入满意度（1-5），默认为3!' }] },
          credit: { rules: [{ required: true, message: '请输入信用度（1-5），默认为3!' }] },
          address: { rules: [{ required: true, message: '请输入联系地址!' }] },
          website: { rules: [{ required: true, message: '请输入网址!' }] },
          zipcode: { rules: [{ required: true, message: '请输入邮编!' }] },
          linkman: { rules: [{ required: true, message: '请输入联系人!' }] },
          mobile: { rules: [{ required: true, message: '请输入手机!' }] },
          tel: { rules: [{ required: true, message: '请输入座机!' }] },
          fax: { rules: [{ required: true, message: '请输入传真!' }] },
          email: { rules: [{ required: true, message: '请输入邮箱!' }] },
          mainProduct: { rules: [{ required: true, message: '请输入主营产品!' }] },
          talk: { rules: [{ required: true, message: '请输入沟通情况!' }] },
          status: { rules: [{ required: true, message: '请输入status!' }] },
          intro: { rules: [{ required: true, message: '请输入介绍!' }] },
          ownerUserId: { rules: [{ required: true, message: '请输入归属人员!' }] },
          nextTime: { rules: [{ required: true, message: '请输入下次沟通时间!' }] },
          connTime: { rules: [{ required: true, message: '请输入最近联系时间!' }] },
          connBody: { rules: [{ required: true, message: '请输入最近沟通内容!' }] },
        },
        url: {
          add: "/crm/customer/add",
          edit: "/crm/customer/edit",
          contactsList: '/crm/customer/queryContactsByMainId',
          traceList: '/crm/customer/queryTraceByMainId',
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
          this.form.setFieldsValue(pick(this.model, 'name', 'customerNo', 'areaId', 'source', 'ecotype', 'level', 'trade', 'satisfy', 'credit', 'address', 'website', 'zipcode', 'linkman', 'mobile', 'tel', 'fax', 'email', 'mainProduct', 'talk', 'status', 'intro', 'ownerUserId', 'nextTime', 'connTime', 'connBody', ))
          // 时间格式化
          this.form.setFieldsValue({ nextTime: this.model.nextTime ? moment(this.model.nextTime) : null })
          this.form.setFieldsValue({ connTime: this.model.connTime ? moment(this.model.connTime) : null })
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
            formData.nextTime = formData.nextTime?formData.nextTime.format('YYYY-MM-DD HH:mm:ss'):null;
            formData.connTime = formData.connTime?formData.connTime.format('YYYY-MM-DD HH:mm:ss'):null;
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