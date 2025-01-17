<template>
  <div class="jp-common-layout page">
    <div class="jp-common-layout-left">
      <div class="jp-common-title">
        <el-row :gutter="5">
          <el-col :span="20">
            <el-input
              placeholder="输入关键字进行过滤"
              size="small"
              v-model="filterText"
            ></el-input>
          </el-col>
          <el-col :span="4">
            <el-button
              type="primary"
              @click="addTreeNode"
              size="small"
              icon="el-icon-plus"
              circle
            ></el-button>
          </el-col>
        </el-row>
      </div>
      <div class="jp-common-el-tree-scrollbar el-scrollbar">
        <div class="el-scrollbar__wrap">
          <div class="el-scrollbar__view">
            <el-tree
              class="filter-tree"
              :data="inventoryTypeTreeData"
              :props="{
                value: 'id', // ID字段名
                label: 'name', // 显示名称
                children: 'children', // 子级字段名
              }"
              default-expand-all
              highlight-current
              node-key="id"
              :filter-node-method="filterNode"
              :expand-on-click-node="false"
              @node-click="handleNodeClick"
              ref="inventoryTypeTree"
            >
              <span class="custom-tree-node" slot-scope="{ node, data }">
                <span>{{ node.label }}</span>
                <span>
                  <el-button
                    type="text"
                    class="tree-item-button"
                    icon="el-icon-plus"
                    @click="() => addChildTreeNode(data)"
                  ></el-button>
                  <el-button
                    type="text"
                    class="tree-item-button"
                    icon="el-icon-edit-outline"
                    @click="() => editTreeNode(data)"
                  ></el-button>
                  <el-button
                    type="text"
                    class="red tree-item-button"
                    icon="el-icon-delete"
                    @click="() => delTreeNode(data)"
                  ></el-button>
                </span>
              </span>
            </el-tree>
          </div>
        </div>
      </div>
    </div>
    <div class="jp-common-layout-center jp-flex-main">
      <el-form
        size="small"
        :inline="true"
        class="query-form"
        ref="searchForm"
        :model="searchForm"
        @keyup.enter.native="refreshList()"
        @submit.native.prevent
      >
        <!-- 搜索框-->
        <el-form-item prop="inventoryType.id">
          <SelectTree
            ref="inventoryType"
            :props="{
              value: 'id', // ID字段名
              label: 'name', // 显示名称
              children: 'children', // 子级字段名
            }"
            size="small"
            url="/basic/inventorytype/inventoryType/treeData"
            :value="searchForm.inventoryType.id"
            :clearable="true"
            :accordion="true"
            @getValue="
              (value) => {
                searchForm.inventoryType.id = value;
              }
            "
          />
        </el-form-item>
        <el-form-item prop="inventoryCode">
          <el-input
            size="small"
            v-model="searchForm.inventoryCode"
            placeholder="存货编码"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item prop="inventoryName">
          <el-input
            size="small"
            v-model="searchForm.inventoryName"
            placeholder="存货名称"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item prop="manufactor">
          <el-input
            size="small"
            v-model="searchForm.manufactor"
            placeholder="厂家"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item prop="preStorageWarehouse.id">
          <GridSelect
            title="选择预存仓库"
            placeholder="请选择预存仓库"
            labelName="name"
            labelValue="id"
            :value="searchForm.preStorageWarehouse.id"
            :limit="1"
            size="small"
            @getValue="
              (value) => {
                searchForm.preStorageWarehouse.id = value;
              }
            "
            :columns="[
              {
                prop: 'name',
                label: '仓库名称',
              },
            ]"
            :searchs="[
              {
                prop: 'name',
                label: '仓库名称',
              },
            ]"
            dataListUrl="/basic/warehouse/wareHouse/list"
            queryEntityUrl="/basic/warehouse/wareHouse/queryById"
          >
          </GridSelect>
        </el-form-item>
        <el-form-item prop="inventoryWarehouse.warehouse.id">
          <GridSelect
            title="选择所在仓库"
            placeholder="请选择所在仓库"
            labelName="name"
            labelValue="id"
            :value="searchForm.inventoryWarehouse.warehouse.id"
            :limit="1"
            size="small"
            @getValue="
              (value) => {
                searchForm.inventoryWarehouse.warehouse.id = value;
              }
            "
            :columns="[
              {
                prop: 'name',
                label: '仓库名称',
              },
            ]"
            :searchs="[
              {
                prop: 'name',
                label: '仓库名称',
              },
            ]"
            dataListUrl="/basic/warehouse/wareHouse/list"
            queryEntityUrl="/basic/warehouse/wareHouse/queryById"
          >
          </GridSelect>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="refreshList()"
            size="small"
            icon="el-icon-search"
            >查询</el-button
          >
          <el-button
            @click="resetSearch()"
            size="small"
            icon="el-icon-refresh-right"
            >重置</el-button
          >
        </el-form-item>
      </el-form>

      <div class="bg-white top">
        <vxe-toolbar :refresh="{ query: refreshList }" export print custom>
          <template #buttons>
            <el-button
              v-if="hasPermission('basic:inventorytype:inventory:add')"
              type="primary"
              size="small"
              icon="el-icon-plus"
              @click="add()"
              >新建</el-button
            >
            <el-button
              v-if="hasPermission('basic:inventorytype:inventory:edit')"
              type="warning"
              size="small"
              icon="el-icon-edit-outline"
              @click="edit()"
              :disabled="
                $refs.inventoryTable &&
                $refs.inventoryTable.getCheckboxRecords().length !== 1
              "
              plain
              >修改
            </el-button>
            <el-button
              v-if="hasPermission('basic:inventorytype:inventory:del')"
              type="danger"
              size="small"
              icon="el-icon-delete"
              @click="del()"
              :disabled="
                $refs.inventoryTable &&
                $refs.inventoryTable.getCheckboxRecords().length === 0
              "
              plain
              >删除
            </el-button>
          </template>
        </vxe-toolbar>
        <div style="height: calc(100% - 80px)">
          <vxe-table
            border="inner"
            auto-resize
            resizable
            height="auto"
            :loading="loading"
            size="small"
            ref="inventoryTable"
            show-header-overflow
            show-overflow
            highlight-hover-row
            :menu-config="{}"
            :print-config="{}"
            :import-config="{}"
            :export-config="{}"
            @sort-change="sortChangeHandle"
            :sort-config="{ remote: true }"
            :data="dataList"
            :checkbox-config="{}"
          >
            <vxe-column type="seq" width="40"></vxe-column>
            <vxe-column type="checkbox" width="40px"></vxe-column>
            <vxe-column field="inventoryCode" sortable title="存货编码">
              <template slot-scope="scope">
                <el-link
                  type="primary"
                  :underline="false"
                  v-if="hasPermission('basic:inventorytype:inventory:edit')"
                  @click="edit(scope.row.id)"
                  >{{ scope.row.inventoryCode }}</el-link
                >
                <el-link
                  type="primary"
                  :underline="false"
                  v-else-if="
                    hasPermission('basic:inventorytype:inventory:view')
                  "
                  @click="view(scope.row.id)"
                >
                  {{ scope.row.inventoryCode }}</el-link
                >
                <span v-else>{{ scope.row.inventoryCode }}</span>
              </template>
            </vxe-column>
            <vxe-column field="inventoryName" sortable title="存货名称">
            </vxe-column>
            <!--  <vxe-column field="measurementMethod" sortable title="计量方式">
              <template slot-scope="scope">
                {{ $dictUtils.getDictLabel("measurement_method", scope.row.measurementMethod, '-') }}
              </template>
            </vxe-column> -->
            <vxe-column field="inventoryType.name" sortable title="存货类型">
            </vxe-column>
            <vxe-column
              field="singalUnit"
              sortable
              title="计量单位"
              width="100"
            >
              <template slot-scope="scope">
                {{
                  $dictUtils.getDictLabel(
                    "sx_metering_unit",
                    scope.row.singalUnit,
                    "-"
                  )
                }}
              </template>
            </vxe-column>
            <vxe-column field="model" sortable title="规格型号"> </vxe-column>
            <!-- <vxe-column field="brand" sortable title="品牌">
            </vxe-column>
            <vxe-column field="manufactor" sortable title="厂家">
            </vxe-column> -->
            <!-- <vxe-column field="preStorageWarehouse.name" sortable title="预存仓库">
              <template slot-scope="scope">
                {{scope.row.preStorageWarehouse && scope.row.preStorageWarehouse.name}}
              </template>
            </vxe-column> -->
            <vxe-column
              field="nventoryAttributeValue"
              sortable
              title="属性值"
              width="200"
            >
            </vxe-column>
            <vxe-column fixed="right" align="center" width="200" title="操作">
              <template slot-scope="scope">
                <el-button
                  v-if="hasPermission('basic:inventorytype:inventory:view')"
                  type="text"
                  icon="el-icon-view"
                  size="small"
                  @click="view(scope.row.id)"
                  >查看</el-button
                >
                <el-button
                  v-if="hasPermission('basic:inventorytype:inventory:edit')"
                  type="text"
                  icon="el-icon-edit"
                  size="small"
                  @click="edit(scope.row.id)"
                  >修改</el-button
                >
                <el-button
                  v-if="hasPermission('basic:inventorytype:inventory:del')"
                  type="text"
                  icon="el-icon-delete"
                  size="small"
                  @click="del(scope.row.id)"
                  >删除</el-button
                >
              </template>
            </vxe-column>
          </vxe-table>
          <vxe-pager
            background
            size="small"
            :current-page="tablePage.currentPage"
            :page-size="tablePage.pageSize"
            :total="tablePage.total"
            :page-sizes="[
              10,
              20,
              100,
              1000,
              { label: '全量数据', value: 1000000 },
            ]"
            :layouts="[
              'PrevPage',
              'JumpNumber',
              'NextPage',
              'FullJump',
              'Sizes',
              'Total',
            ]"
            @page-change="currentChangeHandle"
          >
          </vxe-pager>
        </div>
        <!-- 弹窗, 新增 / 修改 -->
        <InventoryForm
          ref="inventoryForm"
          @refreshDataList="refreshList"
        ></InventoryForm>
        <InventoryTypeForm
          ref="inventoryTypeForm"
          @refreshTree="refreshTree"
        ></InventoryTypeForm>
        <inventory-edit-form
          ref="inventoryEditForm"
          @refreshDataList="refreshList"
        ></inventory-edit-form>
      </div>
    </div>
  </div>
</template>

<script>
import InventoryForm from "./InventoryForm";
import InventoryTypeForm from "./InventoryTypeForm";
import InventoryService from "@/api/basic/inventorytype/InventoryService";
import InventoryTypeService from "@/api/basic/inventorytype/InventoryTypeService";
import SelectTree from "@/components/treeSelect/treeSelect.vue";
import GridSelect from "@/components/gridSelect";
import InventoryEditForm from "./InventoryEditForm.vue";
export default {
  data() {
    return {
      searchForm: {
        inventoryCode: "",
        inventoryName: "",
        manufactor: "",
        preStorageWarehouse: {
          id: "",
        },
        inventoryType: {
          id: "",
        },
        inventoryWarehouse: {
          warehouse: { id: "" },
        },
      },
      filterText: "",
      inventoryTypeTreeData: [],
      selectInventoryTypeName: "",
      dataList: [],
      tablePage: {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        orders: [],
      },
      loading: false,
    };
  },
  components: {
    SelectTree,
    GridSelect,
    InventoryForm,
    InventoryTypeForm,
    InventoryEditForm,
  },
  inventoryService: null,
  InventoryTypeService: null,
  created() {
    this.inventoryService = new InventoryService();
    this.InventoryTypeService = new InventoryTypeService();
  },
  activated() {
    this.refreshTree();
    this.refreshList();
  },
  computed: {},
  watch: {
    filterText(val) {
      this.$refs.inventoryTypeTree.filter(val);
    },
  },
  methods: {
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    refreshTree() {
      this.InventoryTypeService.treeData().then(({ data }) => {
        this.inventoryTypeTreeData = data;
      });
    },
    handleNodeClick(data) {
      this.searchForm.inventoryType.id = data.id;
      this.refreshList();
    },
    addChildTreeNode(node) {
      this.$refs.inventoryTypeForm.init("addChild", {
        id: "",
        parent: {
          id: node.id,
          name: node.name,
        },
      });
    },
    // 新增
    addTreeNode() {
      this.$refs.inventoryTypeForm.init("add", {
        id: "",
        parent: {
          id: "",
          name: "",
        },
      });
    },
    // 修改
    editTreeNode(data) {
      this.$refs.inventoryTypeForm.init("edit", {
        id: data.id,
        parent: {
          id: "",
          name: "",
        },
      });
    },
    delTreeNode(data) {
      this.$confirm(`确定删除所选项吗?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.loading = true;
        this.InventoryTypeService.delete(data.id).then(({ data }) => {
          this.$message.success(data);
          this.loading = false;
          this.refreshTree();
          this.refreshList();
        });
      });
    },
    // 获取数据列表
    refreshList() {
      this.loading = true;
      this.inventoryService
        .list({
          current: this.tablePage.currentPage,
          size: this.tablePage.pageSize,
          orders: this.tablePage.orders,
          // ...this.searchForm,
          "inventoryType.id": this.searchForm.inventoryType.id,
          inventoryCode: this.searchForm.inventoryCode,
          inventoryName: this.searchForm.inventoryName,
          manufactor: this.searchForm.manufactor,
          "preStorageWarehouse.id": this.searchForm.preStorageWarehouse.id,
          "inventoryWarehouse.warehouse.id":
            this.searchForm.inventoryWarehouse.warehouse.id,
        })
        .then(({ data }) => {
          this.dataList = data.records;
          this.tablePage.total = data.total;
          this.loading = false;
        });
    },
    // 当前页
    currentChangeHandle({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.refreshList();
    },
    // 排序
    sortChangeHandle(column) {
      this.tablePage.orders = [];
      if (column.order != null) {
        this.tablePage.orders.push({
          column: this.$utils.toLine(column.property),
          asc: column.order === "asc",
        });
      }
      this.refreshList();
    },
    // 新增
    add() {
      this.$refs.inventoryForm.init("add", "");
    },
    // 修改
    edit(id) {
      id =
        id ||
        this.$refs.inventoryTable.getCheckboxRecords().map((item) => {
          return item.id;
        })[0];
      this.$refs.inventoryEditForm.init("edit", id);
    },
    // 查看
    view(id) {
      this.$refs.inventoryEditForm.init("view", id);
    },
    // 删除
    del(id) {
      let ids =
        id ||
        this.$refs.inventoryTable
          .getCheckboxRecords()
          .map((item) => {
            return item.id;
          })
          .join(",");
      this.$confirm(`确定删除所选项吗?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.loading = true;
        this.inventoryService.delete(ids).then(({ data }) => {
          this.$message.success(data);
          this.refreshList();
          this.loading = false;
        });
      });
    },
    resetSearch() {
      this.$refs.searchForm.resetFields();
      this.filterText = "";
      this.$refs.inventoryTypeTree.setCurrentKey(null);
      this.refreshList();
    },
  },
};
</script>
