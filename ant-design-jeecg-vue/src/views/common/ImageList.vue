<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="业务ID">
              <a-input placeholder="请输入业务ID" v-model="queryParam.businessId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="业务类型: 1/banner图；2/商品">
              <a-input placeholder="请输入业务类型: 1/banner图；2/商品" v-model="queryParam.businessType"></a-input>
            </a-form-item>
          </a-col>
        <template v-if="toggleSearchStatus">
        <a-col :md="6" :sm="8">
            <a-form-item label="图片类型: 1/主图,2/详细图">
              <a-input placeholder="请输入图片类型: 1/主图,2/详细图" v-model="queryParam.type"></a-input>
            </a-form-item>
          </a-col>
        </template>
          <a-col :md="6" :sm="8" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
<!--      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>-->
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
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
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <image-modal ref="modalForm" @ok="modalFormOk"></image-modal>
  </a-card>
</template>

<script>
  import ImageModal from './modules/ImageModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "ImageList",
    mixins:[JeecgListMixin],
    components: {
      ImageModal
    },
    data () {
      return {
        description: '图片管理管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
           },
		   {
            title: '业务ID',
            align:"center",
            dataIndex: 'businessId'
           },
		   {
            title: '业务类型',
            align:"center",
            dataIndex: 'businessType',
            customRender: function(text) {
               if (text == 1) {
                 return 'Banner图'
               } else if (text == 2) {
                 return '商品图'
               } else {
                 return text
               }
            }
           },
		   {
            title: '图片类型',
            align:"center",
            dataIndex: 'type',
            customRender: function(text) {
               if (text == 1) {
                 return '主图'
               } else if (text == 2) {
                 return '详细图'
               } else {
                 return text
               }
             }
           },
		   {
            title: '图片路径',
            align:"center",
            dataIndex: 'url'
           },
		   {
            title: '排序',
            align:"center",
            dataIndex: 'sort'
           },
		   {
            title: '备注',
            align:"center",
            dataIndex: 'remark'
           },
		   {
            title: '状态',
            align:"center",
            dataIndex: 'status',
            customRender: function(text) {
               if (text == 1) {
                 return '有效'
               } else if (text == 0) {
                 return '删除'
               } else {
                 return text
               }
            }

           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/system/image/list",
          delete: "/system/image/delete",
          deleteBatch: "/system/image/deleteBatch",
          exportXlsUrl: "system/image/exportXls",
          importExcelUrl: "system/image/importExcel",
       },
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    methods: {
     
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>