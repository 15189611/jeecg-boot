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
          label="标题">
          <a-input placeholder="请输入标题" v-decorator="['title', validatorRules.title ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="商品描述">
          <a-input placeholder="请输入商品描述" v-decorator="['description', validatorRules.description ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="类型">
          <a-select
            v-decorator="[ 'categoryId',validatorRules.categoryId, {}]"
            placeholder="请选择状态"
            :disabled="disableSubmit">
            <a-select-option :value="0">白毫银针</a-select-option>
            <a-select-option :value="1">白牡丹</a-select-option>
            <a-select-option :value="2">贡眉</a-select-option>
            <a-select-option :value="3">寿眉</a-select-option>
          </a-select>

        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="销售价">
          <a-input-number v-decorator="[ 'sellingPrice', validatorRules.sellingPrice ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="原价格">
          <a-input-number v-decorator="[ 'discountPrice', validatorRules.discountPrice ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="成本价">
          <a-input-number v-decorator="[ 'costPrice', validatorRules.costPrice ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="是否推荐">
          <a-select
            v-decorator="[ 'isRecommend',validatorRules.isRecommend, {}]"
            placeholder="请选择状态"
            :disabled="disableSubmit">
            <a-select-option :value="0">否</a-select-option>
            <a-select-option :value="1">是</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="是否热销">
          <a-select
            v-decorator="[ 'isHot',validatorRules.isHot, {}]"
            placeholder="请选择状态"
            :disabled="disableSubmit">
            <a-select-option :value="0">否</a-select-option>
            <a-select-option :value="1">是</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="是否新品">
          <a-select
            v-decorator="[ 'isNew',validatorRules.isNew, {}]"
            placeholder="请选择状态"
            :disabled="disableSubmit">
            <a-select-option :value="0">否</a-select-option>
            <a-select-option :value="1">是</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="是否免邮:0-否,1-是">
          <a-select
            v-decorator="[ 'isFree',validatorRules.isFree, {}]"
            placeholder="请选择状态"
            :disabled="disableSubmit">
            <a-select-option :value="0">否</a-select-option>
            <a-select-option :value="1">是</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="库存">
          <a-input-number v-decorator="[ 'inventory', validatorRules.inventory ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="销量">
          <a-input-number v-decorator="[ 'sells', validatorRules.sells ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="状态">
          <a-select
            v-decorator="[ 'status',validatorRules.status, {}]"
            placeholder="请选择状态"
            :disabled="disableSubmit">
            <a-select-option :value="0">下架</a-select-option>
            <a-select-option :value="1">上架</a-select-option>
          </a-select>
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
    name: 'ProductModal',
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
          title: { rules: [{ required: true, message: '请输入描述!' }] },
          description: { rules: [{ required: true, message: '请输入商品标题!' }] },
          discountPrice: { rules: [{ required: true, message: '请输入优惠价!' }] },
          categoryId: { rules: [{ required: true, message: '请输入categoryId!' }] },
          sellingPrice: { rules: [{ required: true, message: '请输入销售价!' }] },
          costPrice: { rules: [{ required: true, message: '请输入成本价!' }] },
          isRecommend: { rules: [{ required: true, message: '请输入是否推荐:0-否,1-是!' }] },
          isHot: { rules: [{ required: true, message: '请输入是否热销:0-否,1-是!' }] },
          isNew: { rules: [{ required: true, message: '请输入是否新品:0-否,1-是!' }] },
          isFree: { rules: [{ required: true, message: '请输入是否免邮:0-否,1-是!' }] },
          weight: { rules: [{ required: true, message: '请输入重量!' }] },
          volume: { rules: [{ required: true, message: '请输入尺寸!' }] },
          volType: { rules: [{ required: true, message: '请输入尺寸单位!' }] },
          inventory: { rules: [{ required: true, message: '请输入库存!' }] },
          sells: { rules: [{ required: true, message: '请输入销量!' }] },
          status: { rules: [{ required: true, message: '请输入状态:1-上架,0-下架!' }] },
        },
        url: {
          add: "/mall/product/add",
          edit: "/mall/product/edit",
          imageList: '/mall/product/queryImageByMainId',
          collectionList: '/mall/product/queryCollectionByMainId',
          productDetailList: '/mall/product/queryProductDetailByMainId',
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
          this.form.setFieldsValue(pick(this.model, 'title', 'description', 'discountPrice', 'categoryId', 'sellingPrice', 'costPrice', 'isRecommend', 'isHot', 'isNew', 'isFree', 'weight', 'volume', 'volType', 'inventory', 'sells', 'status', ))
          // 时间格式化
          this.form.setFieldsValue({ onShelvesTime: this.model.onShelvesTime ? moment(this.model.onShelvesTime) : null })
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
            formData.onShelvesTime = formData.onShelvesTime?formData.onShelvesTime.format('YYYY-MM-DD HH:mm:ss'):null;
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