<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="24">
            <a-form-item label="用户名">
              <a-input placeholder="请输入用户名" v-model="queryParam.userName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="昵称">
              <a-input placeholder="请输入昵称" v-model="queryParam.nickName"></a-input>
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
      <a-tab-pane tab="详细信息" :key="refKeys[0]" :forceRender="true">
        <userDetail-List ref="UserDetailList"></userDetail-List>
      </a-tab-pane>
      <a-tab-pane tab="授权信息" :key="refKeys[1]" :forceRender="true">
        <userAuth-List ref="UserAuthList"></userAuth-List>
      </a-tab-pane>

    </a-tabs>

    <!-- 表单区域 -->
    <userAccount-modal ref="modalForm" @ok="modalFormOk"/>

  </a-card>
</template>

<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import UserAccountModal from './modules/UserAccountModal'
  import {deleteAction} from '@/api/manage'
  import UserAuthList from './UserAuthList'
  import UserAuthModal from './modules/UserAuthModal'
  import UserDetailList from './UserDetailList'
  import UserDetailModal from './modules/UserDetailModal'

  export default {
    name: "UserAccountList",
    mixins: [JeecgListMixin],
    components: {
      UserAccountModal,
      UserAuthModal,
      UserAuthList,
      UserDetailModal,
      UserDetailList,
    },
    data () {
      return {
        refKeys: ['userAuth', 'userDetail', ],
        description: '用户信息管理页面',
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
            title: '用户名',
            align:"center",
            dataIndex: 'userName'
          },
          {
            title: '昵称',
            align:"center",
            dataIndex: 'nickName'
          },
          {
            title: '头像',
            align:"center",
            dataIndex: 'avatar'
          },
          {
            title: '是否锁定: 0/锁定,1/正常',
            align:"center",
            dataIndex: 'locked'
          },
          {
            title: '最后登录时间',
            align:"center",
            dataIndex: 'lastVisitTime'
          },
          {
            title: '注册时间',
            align:"center",
            dataIndex: 'registerTime'
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
          list: "/mall/userAccount/list",
          delete: "/mall/userAccount/delete",
          deleteBatch: "/mall/userAccount/deleteBatch",
          exportXlsUrl: "mall/userAccount/exportXls",
          importExcelUrl: "mall/userAccount/importExcel",
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
        this.$refs.UserAuthList.getMain(this.selectedRowKeys[0]);
        this.$refs.UserDetailList.getMain(this.selectedRowKeys[0]);
      },
      onClearSelected() {
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.UserAuthList.queryParam.mainId = null;
        this.$refs.UserAuthList.loadData();
        this.$refs.UserAuthList.selectedRowKeys = [];
        this.$refs.UserAuthList.selectionRows = [];
        this.$refs.UserDetailList.queryParam.mainId = null;
        this.$refs.UserDetailList.loadData();
        this.$refs.UserDetailList.selectedRowKeys = [];
        this.$refs.UserDetailList.selectionRows = [];
      },

      handleDelete: function (id) {
        var that = this;
        deleteAction(that.url.delete, {id: id}).then((res) => {
          if (res.success) {
            that.$message.success(res.message);
            that.loadData();
            this.$refs.UserAuthList.loadData();
            this.$refs.UserDetailList.loadData();
          } else {
            that.$message.warning(res.message);
          }
        });
      },
      searchQuery:function(){
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.UserAuthList.queryParam.mainId = null;
        this.$refs.UserAuthList.loadData();
        this.$refs.UserAuthList.selectedRowKeys = [];
        this.$refs.UserAuthList.selectionRows = [];
        this.$refs.UserDetailList.queryParam.mainId = null;
        this.$refs.UserDetailList.loadData();
        this.$refs.UserDetailList.selectedRowKeys = [];
        this.$refs.UserDetailList.selectionRows = [];
        this.loadData();
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>