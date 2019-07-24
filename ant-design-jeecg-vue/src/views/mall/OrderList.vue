<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="24">
            <a-form-item label="订单编号">
              <a-input placeholder="请输入用户ID" v-model="queryParam.orderNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="交易流水">
              <a-input placeholder="请输入交易流水" v-model="queryParam.tradeNo"></a-input>
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
<!--      <a-button type="primary" icon="download" @click="handleExportXls('订单信息')">导出</a-button>-->
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button type="primary" icon="import">导入</a-button>-->
<!--      </a-upload>-->
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
      <a-tab-pane tab="订单商品" :key="refKeys[0]" :forceRender="true">
        <orderProduct-List ref="OrderProductList"></orderProduct-List>
      </a-tab-pane>
    </a-tabs>

    <!-- 表单区域 -->
    <order-modal ref="modalForm" @ok="modalFormOk"/>

  </a-card>
</template>

<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import OrderModal from './modules/OrderModal'
  import {deleteAction} from '@/api/manage'
  import OrderProductList from './OrderProductList'
  import OrderProductModal from './modules/OrderProductModal'

  export default {
    name: "OrderList",
    mixins: [JeecgListMixin],
    components: {
      OrderModal,
      OrderProductModal,
      OrderProductList,
    },
    data () {
      return {
        refKeys: ['orderProduct', ],
        description: '订单信息管理页面',
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
            title: '订单编号',
            align:"center",
            dataIndex: 'orderNo'
          },
          {
            title: '交易流水',
            align:"center",
            dataIndex: 'tradeNo'
          },
          {
            title: '订单时间',
            align:"center",
            dataIndex: 'orderTime'
          },
          {
            title: '订单总金额',
            align:"center",
            dataIndex: 'totalAmount'
          },
          {
            title: '优惠金额',
            align:"center",
            dataIndex: 'discountAmount'
          },
          {
            title: '应支付金额',
            align:"center",
            dataIndex: 'realAmount'
          },
          {
            title: '实际支付金额',
            align:"center",
            dataIndex: 'payAmount'
          },
          {
            title: '支付状态',
            align:"center",
            dataIndex: 'payStatus'
          },
          {
            title: '收件人',
            align:"center",
            dataIndex: 'consignee'
          },
          {
            title: '订单状态',
            align:"center",
            dataIndex: 'status',
            customRender: function(text) {
              if (text == 0) {
                return '创建'
              } else if (text == 1) {
                return '待支付'
              } else if (text == 2) {
                return '已支付，待发货'
              }else if (text == 3) {
                return '已发货，待签收'
              }else if (text == 4) {
                return '已签收，完成'
              }else {
                return text
              }
            }
          },
          {
            title: '备注',
            align:"center",
            dataIndex: 'remark'
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
          list: "/mall/order/list",
          delete: "/mall/order/delete",
          deleteBatch: "/mall/order/deleteBatch",
          exportXlsUrl: "mall/order/exportXls",
          importExcelUrl: "mall/order/importExcel",
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
        this.$refs.OrderProductList.getMain(this.selectedRowKeys[0]);
      },
      onClearSelected() {
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.OrderProductList.queryParam.mainId = null;
        this.$refs.OrderProductList.loadData();
        this.$refs.OrderProductList.selectedRowKeys = [];
        this.$refs.OrderProductList.selectionRows = [];
      },

      handleDelete: function (id) {
        var that = this;
        deleteAction(that.url.delete, {id: id}).then((res) => {
          if (res.success) {
            that.$message.success(res.message);
            that.loadData();
            this.$refs.OrderProductList.loadData();
          } else {
            that.$message.warning(res.message);
          }
        });
      },
      searchQuery:function(){
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.OrderProductList.queryParam.mainId = null;
        this.$refs.OrderProductList.loadData();
        this.$refs.OrderProductList.selectedRowKeys = [];
        this.$refs.OrderProductList.selectionRows = [];
        this.loadData();
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>