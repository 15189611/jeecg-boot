<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="24">
            <a-form-item label="描述">
              <a-input placeholder="请输入描述" v-model="queryParam.title"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="商品标题">
              <a-input placeholder="请输入商品标题" v-model="queryParam.description"></a-input>
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
      <a-button type="primary" icon="download" @click="handleExportXls('商品信息')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
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
      <a-tab-pane tab="图片" :key="refKeys[0]" :forceRender="true">
        <image-List ref="ImageList"></image-List>
      </a-tab-pane>
      <a-tab-pane tab="收藏信息" :key="refKeys[1]" :forceRender="true">
        <collection-List ref="CollectionList"></collection-List>
      </a-tab-pane>
      <a-tab-pane tab="商品详细信息" :key="refKeys[2]" :forceRender="true">
        <productDetail-List ref="ProductDetailList"></productDetail-List>
      </a-tab-pane>
    </a-tabs>

    <!-- 表单区域 -->
    <product-modal ref="modalForm" @ok="modalFormOk"/>

  </a-card>
</template>

<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import ProductModal from './modules/ProductModal'
  import {deleteAction} from '@/api/manage'
  import ImageList from './ImageList'
  import ImageModal from './modules/ImageModal'
  import CollectionList from './CollectionList'
  import CollectionModal from './modules/CollectionModal'
  import ProductDetailList from './ProductDetailList'
  import ProductDetailModal from './modules/ProductDetailModal'

  export default {
    name: "ProductList",
    mixins: [JeecgListMixin],
    components: {
      ProductModal,
      ImageModal,
      ImageList,
      CollectionModal,
      CollectionList,
      ProductDetailModal,
      ProductDetailList,
    },
    data () {
      return {
        refKeys: ['image', 'collection','productDetail', ],
        description: '商品信息管理页面',
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
            title: '标题',
            align:"center",
            dataIndex: 'title'
          },
          {
            title: '商品描述',
            align:"center",
            dataIndex: 'description'
          },
          {
            title: '类型',
            align:"center",
            dataIndex: 'categoryId',
            customRender: function(text) {
              if (text == 0) {
                return '白毫银针'
              } else if (text == 1) {
                return '白牡丹'
              } else if (text == 2) {
                return '贡眉'
              } else if (text == 3) {
                return '寿眉'
              } else {
                return text
              }
            }
          },

          {
            title: '销售价',
            align:"center",
            dataIndex: 'sellingPrice'
          },
          {
            title: '原价格',
            align:"center",
            dataIndex: 'discountPrice'
          },
          {
            title: '成本价',
            align:"center",
            dataIndex: 'costPrice'
          },
          {
            title: '是否推荐',
            align:"center",
            dataIndex: 'isRecommend',
            customRender: function(text) {
              if (text == 0) {
                return '停用'
              } else if (text == 1) {
                return '启用'
              } else {
                return text
              }
            }
          },
          {
            title: '是否热销',
            align:"center",
            dataIndex: 'isHot',
            customRender: function(text) {
              if (text == 0) {
                return '否'
              } else if (text == 1) {
                return '是'
              } else {
                return text
              }
            }
          },
          {
            title: '是否新品',
            align:"center",
            dataIndex: 'isNew',
            customRender: function(text) {
              if (text == 0) {
                return '否'
              } else if (text == 1) {
                return '是'
              } else {
                return text
              }
            }
          },
          {
            title: '是否免邮',
            align:"center",
            dataIndex: 'isFree',
            customRender: function(text) {
              if (text == 0) {
                return '否'
              } else if (text == 1) {
                return '是'
              } else {
                return text
              }
            }
          },
          {
            title: '库存',
            align:"center",
            dataIndex: 'inventory'
          },
          {
            title: '销量',
            align:"center",
            dataIndex: 'sells'
          },
          {
            title: '状态',
            align:"center",
            dataIndex: 'status_dictText'
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
          list: "/mall/product/list",
          delete: "/mall/product/delete",
          deleteBatch: "/mall/product/deleteBatch",
          exportXlsUrl: "mall/product/exportXls",
          importExcelUrl: "mall/product/importExcel",
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
        this.$refs.ImageList.getMain(this.selectedRowKeys[0]);
        this.$refs.CollectionList.getMain(this.selectedRowKeys[0]);
        this.$refs.ProductDetailList.getMain(this.selectedRowKeys[0]);
      },
      onClearSelected() {
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.ImageList.queryParam.mainId = null;
        this.$refs.ImageList.loadData();
        this.$refs.ImageList.selectedRowKeys = [];
        this.$refs.ImageList.selectionRows = [];
        this.$refs.CollectionList.queryParam.mainId = null;
        this.$refs.CollectionList.loadData();
        this.$refs.CollectionList.selectedRowKeys = [];
        this.$refs.CollectionList.selectionRows = [];
        this.$refs.ProductDetailList.queryParam.mainId = null;
        this.$refs.ProductDetailList.loadData();
        this.$refs.ProductDetailList.selectedRowKeys = [];
        this.$refs.ProductDetailList.selectionRows = [];
      },

      handleDelete: function (id) {
        var that = this;
        deleteAction(that.url.delete, {id: id}).then((res) => {
          if (res.success) {
            that.$message.success(res.message);
            that.loadData();
            this.$refs.ImageList.loadData();
            this.$refs.CollectionList.loadData();
            this.$refs.ProductDetailList.loadData();
          } else {
            that.$message.warning(res.message);
          }
        });
      },
      searchQuery:function(){
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.ImageList.queryParam.mainId = null;
        this.$refs.ImageList.loadData();
        this.$refs.ImageList.selectedRowKeys = [];
        this.$refs.ImageList.selectionRows = [];
        this.$refs.CollectionList.queryParam.mainId = null;
        this.$refs.CollectionList.loadData();
        this.$refs.CollectionList.selectedRowKeys = [];
        this.$refs.CollectionList.selectionRows = [];
        this.$refs.ProductDetailList.queryParam.mainId = null;
        this.$refs.ProductDetailList.loadData();
        this.$refs.ProductDetailList.selectedRowKeys = [];
        this.$refs.ProductDetailList.selectionRows = [];
        this.loadData();
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>