<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">

    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :span="6">
            <a-form-item label="标题">
              <a-input placeholder="请输入标题查询" v-model="queryParam.title"></a-input>
            </a-form-item>
          </a-col>

          <a-col :span="6">
            <a-form-item label="描述">
              <a-input placeholder="请输入描述查询" v-model="queryParam.description"></a-input>
            </a-form-item>
          </a-col>

          <a-col :span="6" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchByquery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <a-table
      ref="table"
      rowKey="id"
      :columns="columns"
      :dataSource="dataSource"
      :pagination="ipagination"
      :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
      @change="handleTableChange"
    >

    </a-table>
  </a-modal>
</template>

<script>
  import { filterObj } from '@/utils/util';
  import { queryProductList } from '@/api/api'

  export default {
    name: "SelectProductListModal",
    components: {
    },
    data () {
      return {
        title:"选择Banner关联商品",
        queryParam: {},
        columns: [
          {
            title: '标题',
            align:"center",
            dataIndex: 'title'
          },
          {
            title: '描述',
            align:"center",
            dataIndex: 'description'
          },
          {
            title: 'categoryId',
            align:"center",
            dataIndex: 'categoryId'
          },
          {
            title: '优惠价',
            align:"center",
            dataIndex: 'discountPrice'
          },
          {
            title: '销售价',
            align:"center",
            dataIndex: 'sellingPrice'
          },
          {
            title: '成本价',
            align:"center",
            dataIndex: 'costPrice'
          },
        ],
        dataSource:[],
        ipagination:{
          current: 1,
          pageSize: 5,
          pageSizeOptions: ['5', '10', '20'],
          showTotal: (total, range) => {
            return range[0] + "-" + range[1] + " 共" + total + "条"
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
        isorter:{
          column: 'createTime',
          order: 'desc',
        },
        selectedRowKeys: [],
        selectionRows: [],
        visible:false,
        toggleSearchStatus:false,
      }
    },
    created() {
      this.loadData();
    },
    methods: {
      add (selectUser,userIds) {
        this.visible = true;
        this.edit(selectUser,userIds);
      },
      edit(selectUser,userIds){
        this.selectionRows = selectUser;
        this.selectedRowKeys = userIds.split(',');
      },
      loadData (arg){
        if(arg===1){
          this.ipagination.current = 1;
        }
        let params = this.getQueryParams();//查询条件
        queryProductList(params).then((res)=>{
          if(res.success){
            this.dataSource = res.result.records;
            this.ipagination.total = res.result.total;
          }
        })
      },
      getQueryParams(){
        let param = Object.assign({}, this.queryParam,this.isorter);
        param.field = this.getQueryField();
        param.current = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        return filterObj(param);
      },
      getQueryField(){
        let str = "id,";
        for(let a = 0;a<this.columns.length;a++){
          str+=","+this.columns[a].dataIndex;
        }
        return str;
      },
      onSelectChange (selectedRowKeys,selectionRows) {
        this.selectedRowKeys = selectedRowKeys;
        console.log(this.selectedRowKeys);
        this.selectionRows = selectionRows;
      },
      searchReset(){
        let that = this;
        Object.keys(that.queryParam).forEach(function(key){
          that.queryParam[key] = '';
        });
        that.loadData(1);
      },
      handleTableChange(pagination, filters, sorter){
        //TODO 筛选
        if (Object.keys(sorter).length>0){
          this.isorter.column = sorter.field;
          this.isorter.order = "ascend"==sorter.order?"asc":"desc"
        }
        this.ipagination = pagination;
        this.loadData();
      },
      handleCancel () {
        this.selectionRows = [];
        this.selectedRowKeys = [];
        this.visible = false;
      },
      handleOk () {
        this.$emit("choseProduct",this.selectionRows);
        this.handleCancel();
      },
      searchByquery(){
        this.loadData(1);
      },
      searchReset(){
        let that = this;
        Object.keys(that.queryParam).forEach(function(key){
          that.queryParam[key] = '';
        });
        that.loadData(1);
      },
      handleToggleSearch(){
        this.toggleSearchStatus = !this.toggleSearchStatus;
      },
    }
  }
</script>
<style scoped>
  .ant-card-body .table-operator{
    margin-bottom: 18px;
  }

  .ant-table-tbody .ant-table-row td{
    padding-top:15px;
    padding-bottom:15px;
  }
  .anty-row-operator button{margin: 0 5px}
  .ant-btn-danger{background-color: #ffffff}

  .ant-modal-cust-warp{height: 100%}
  .ant-modal-cust-warp .ant-modal-body{height:calc(100% - 110px) !important;overflow-y: auto}
  .ant-modal-cust-warp .ant-modal-content{height:90% !important;overflow-y: hidden}

  .anty-img-wrap{height:25px;position: relative;}
  .anty-img-wrap > img{max-height:100%;}
</style>