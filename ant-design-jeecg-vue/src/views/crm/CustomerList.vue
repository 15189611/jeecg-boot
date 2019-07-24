<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="24">
            <a-form-item label="客户名称">
              <a-input placeholder="请输入客户名称" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="客户编号">
              <a-input placeholder="请输入客户编号" v-model="queryParam.customerNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
          <a-icon type="delete"/>删除
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 
          <a-icon type="down" />
        </a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i>
        <span>已选择</span>
        <a style="font-weight: 600">
          {{ selectedRowKeys.length }}
        </a>
        <span>项</span>
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange,type:type}"
        @change="handleTableChange"
        :customRow="clickThenCheck">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
            <a>删除</a>
          </a-popconfirm>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->
    
    <a-tabs defaultActiveKey="1">
      <a-tab-pane tab="联系人" :key="refKeys[0]" :forceRender="true">
        <contacts-List ref="ContactsList"></contacts-List>
      </a-tab-pane>
      <a-tab-pane tab="跟进信息" :key="refKeys[1]" :forceRender="true">
        <trace-List ref="TraceList"></trace-List>
      </a-tab-pane>
    </a-tabs>

    <!-- 表单区域 -->
    <customer-modal ref="modalForm" @ok="modalFormOk"/>

  </a-card>
</template>

<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import CustomerModal from './modules/CustomerModal'
  import {deleteAction} from '@/api/manage'
  import ContactsList from './ContactsList'
  import ContactsModal from './modules/ContactsModal'
  import TraceList from './TraceList'
  import TraceModal from './modules/TraceModal'

  export default {
    name: "CustomerList",
    mixins: [JeecgListMixin],
    components: {
      CustomerModal,
      ContactsModal,
      ContactsList,
      TraceModal,
      TraceList,
    },
    data () {
      return {
        refKeys: ['contacts', 'trace', ],
        description: '客户信息管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key: 'rowIndex',
            width: 60,
            align: "center",
            customRender:function (t, r, index) {
              return parseInt(index)+1;
            }
          },
          {
            title: '客户名称',
            align:"center",
            dataIndex: 'name'
          },
          {
            title: '客户编号',
            align:"center",
            dataIndex: 'customerNo'
          },
          {
            title: '所在地区',
            align:"center",
            dataIndex: 'areaId'
          },
          {
            title: '客户来源',
            align:"center",
            dataIndex: 'source_dictText'
          },
          {
            title: '经济类型',
            align:"center",
            dataIndex: 'ecotype'
          },
          {
            title: '客户等级',
            align:"center",
            dataIndex: 'level'
          },
          {
            title: '客户行业',
            align:"center",
            dataIndex: 'trade'
          },
          {
            title: '满意度（1-5），默认为3',
            align:"center",
            dataIndex: 'satisfy'
          },
          {
            title: '信用度（1-5），默认为3',
            align:"center",
            dataIndex: 'credit'
          },
          {
            title: '联系地址',
            align:"center",
            dataIndex: 'address'
          },
          {
            title: '网址',
            align:"center",
            dataIndex: 'website'
          },
          {
            title: '邮编',
            align:"center",
            dataIndex: 'zipcode'
          },
          {
            title: '联系人',
            align:"center",
            dataIndex: 'linkman'
          },
          {
            title: '手机',
            align:"center",
            dataIndex: 'mobile'
          },
          {
            title: '座机',
            align:"center",
            dataIndex: 'tel'
          },
          {
            title: '传真',
            align:"center",
            dataIndex: 'fax'
          },
          {
            title: '邮箱',
            align:"center",
            dataIndex: 'email'
          },
          {
            title: '主营产品',
            align:"center",
            dataIndex: 'mainProduct'
          },
          {
            title: '沟通情况',
            align:"center",
            dataIndex: 'talk'
          },
          {
            title: 'status',
            align:"center",
            dataIndex: 'status'
          },
          {
            title: '介绍',
            align:"center",
            dataIndex: 'intro'
          },
          {
            title: '归属人员',
            align:"center",
            dataIndex: 'ownerUserId'
          },
          {
            title: '下次沟通时间',
            align:"center",
            dataIndex: 'nextTime'
          },
          {
            title: '最近联系时间',
            align:"center",
            dataIndex: 'connTime'
          },
          {
            title: '最近沟通内容',
            align:"center",
            dataIndex: 'connBody'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
 
        //操作url
        type: "radio",
        url: {
          list: "/crm/customer/list",
          delete: "/crm/customer/delete",
          deleteBatch: "/crm/customer/deleteBatch",
          exportXlsUrl: "crm/customer/exportXls",
          importExcelUrl: "crm/customer/importExcel",
         },
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
		clickThenCheck(record) {
        return {
          on: {
            click: () => {
              this.onSelectChange(record.id.split(","), [record]);
            }
          }
        };
      },
      onSelectChange(selectedRowKeys, selectionRows) {
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
        this.$refs.ContactsList.getMain(this.selectedRowKeys[0]);
        this.$refs.TraceList.getMain(this.selectedRowKeys[0]);
      },
      onClearSelected() {
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.ContactsList.queryParam.mainId = null;
        this.$refs.ContactsList.loadData();
        this.$refs.ContactsList.selectedRowKeys = [];
        this.$refs.ContactsList.selectionRows = [];
        this.$refs.TraceList.queryParam.mainId = null;
        this.$refs.TraceList.loadData();
        this.$refs.TraceList.selectedRowKeys = [];
        this.$refs.TraceList.selectionRows = [];
      },

      handleDelete: function (id) {
        var that = this;
        deleteAction(that.url.delete, {id: id}).then((res) => {
          if (res.success) {
            that.$message.success(res.message);
            that.loadData();
            this.$refs.ContactsList.loadData();
            this.$refs.TraceList.loadData();
          } else {
            that.$message.warning(res.message);
          }
        });
      },
      searchQuery:function(){
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.ContactsList.queryParam.mainId = null;
        this.$refs.ContactsList.loadData();
        this.$refs.ContactsList.selectedRowKeys = [];
        this.$refs.ContactsList.selectionRows = [];
        this.$refs.TraceList.queryParam.mainId = null;
        this.$refs.TraceList.loadData();
        this.$refs.TraceList.selectedRowKeys = [];
        this.$refs.TraceList.selectionRows = [];
        this.loadData();
      }
    }
  }
</script>
<style scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 3px
  }
  .ant-card-body .table-operator {
    margin-bottom: 18px;
  }

  .ant-table-tbody .ant-table-row td {
    padding-top: 15px;
    padding-bottom: 15px;
  }

  .anty-row-operator button {
    margin: 0 5px
  }

  .ant-btn-danger {
    background-color: #ffffff
  }

  .ant-modal-cust-warp {
    height: 100%
  }

  .ant-modal-cust-warp .ant-modal-body {
    height: calc(100% - 110px) !important;
    overflow-y: auto
  }

  .ant-modal-cust-warp .ant-modal-content {
    height: 90% !important;
    overflow-y: hidden
  }
</style>