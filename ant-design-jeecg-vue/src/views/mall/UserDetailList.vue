<template>
  <a-card :bordered="false">

    <!-- 操作按钮区域 -->
    <div class="table-operator" :md="24" :sm="24" style="margin: 0px 0px 0px 0px">
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

<!--        <span slot="action" slot-scope="text, record">-->
<!--          <a @click="handleEdit(record)">编辑</a>-->
<!--          <a-divider type="vertical"/>-->
<!--          <a-dropdown>-->
<!--            <a class="ant-dropdown-link">-->
<!--              更多 <a-icon type="down"/>-->
<!--            </a>-->
<!--            <a-menu slot="overlay">-->
<!--              <a-menu-item>-->
<!--                <a href="javascript:;" @click="handleDetail(record)">详情</a>-->
<!--              </a-menu-item>-->
<!--              <a-menu-item>-->
<!--                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">-->
<!--                  <a>删除</a>-->
<!--                </a-popconfirm>-->
<!--              </a-menu-item>-->
<!--            </a-menu>-->
<!--          </a-dropdown>-->
<!--        </span>-->

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <UserDetail-modal ref="modalForm" @ok="modalFormOk"/>
  </a-card>
</template>

<script>
  import UserDetailModal from './modules/UserDetailModal'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import {getAction} from '@/api/manage'

  export default {
    name: "UserDetailList",
    mixins: [JeecgListMixin],
    components: {
      UserDetailModal,
    },
    data() {
      return {
        description: '信息',
        // 表头
        columns: [
          {
            title: '性别 0/男,1/女',
            align:"center",
            dataIndex: 'gender'
          },
          {
            title: '姓名',
            align:"center",
            dataIndex: 'name'
          },
          {
            title: '手机',
            align:"center",
            dataIndex: 'mobile'
          },
          {
            title: '邮箱',
            align:"center",
            dataIndex: 'email'
          },
          {
            title: '生日',
            align:"center",
            dataIndex: 'birthdate'
          },
          {
            title: '身份证号',
            align:"center",
            dataIndex: 'idCard'
          },
          {
            title: '地址',
            align:"center",
            dataIndex: 'address'
          }
        //  ,{
        //   title: '操作',
        //   key: 'operation',
        //   align: "center",
        //   width: 130,
        //   scopedSlots: {customRender: 'action'},
        // }
        ],
        url: {
          list: "/mall/userAccount/listUserDetailByMainId",
          delete: "/mall/userAccount/deleteUserDetail",
          deleteBatch: "/mall/userAccount/deleteBatchUserDetail",
        }
      }
    },
    methods: {
      loadData(arg) {
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        var params = this.getQueryParams();
        getAction(this.url.list, {mainId: params.mainId}).then((res) => {
          if (res.success) {
            this.dataSource = res.result;
          } else {
            this.dataSource = null;
          }
        })
      },
      getMain(mainId) {
        this.queryParam.mainId = mainId;
        this.loadData(1);
      },
      handleAdd: function () {
        this.$refs.modalForm.add(this.queryParam.mainId);
        this.$refs.modalForm.title = "添加详细信息";
      },
    }
  }
</script>
<style scoped>
  .ant-card {
    margin-left: -30px;
    margin-right: -30px;
  }
</style>
