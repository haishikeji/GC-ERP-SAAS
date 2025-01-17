<template>
  <div>
    <el-dialog
      title="存货选择"
      width="80%"
      :close-on-click-modal="false"
      :append-to-body="true"
      v-dialogDrag
      class="userDialog"
      :visible.sync="visible"
    >
      <el-container style="height: 600px">
        <el-aside width="200px">
          <el-card class="org">
            <div slot="header" class="clearfix">
              <el-input
                placeholder="输入关键字进行过滤"
                size="small"
                v-model="filterText"
              >
              </el-input>
            </div>

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
            </el-tree>
          </el-card>
        </el-aside>

        <el-container>
          <el-header style="font-size: 12px; height: 30px">
            <el-form
              size="small"
              :inline="true"
              class="query-form"
              ref="searchForm"
              :model="searchForm"
              style="display: flex"
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
              </el-form-item>
              <el-form-item>
                <el-button
                  @click="resetSearch()"
                  size="small"
                  icon="el-icon-refresh-right"
                  >重置</el-button
                >
              </el-form-item>
            </el-form>
          </el-header>

          <el-main>
            <el-table
              :data="dataList"
              v-loading="loading"
              size="small"
              border
              ref="userTable"
              @selection-change="selectionChangeHandle"
              @sort-change="sortChangeHandle"
              height="calc(100% - 40px)"
              style="width: 100%"
            >
              <el-table-column
                header-align="center"
                align="center"
                v-if="limit <= 1"
                width="50"
              >
                <template slot-scope="scope">
                  <el-radio
                    :label="scope.row.inventoryWarehouse.id"
                    :value="
                      dataListAllSelections[0] &&
                      dataListAllSelections[0].inventoryWarehouse.id
                    "
                    @change.native="getTemplateRow(scope.$index, scope.row)"
                    ><span></span
                  ></el-radio>
                </template>
              </el-table-column>
              <el-table-column
                type="selection"
                header-align="center"
                v-if="limit > 1"
                align="center"
                width="50"
              >
              </el-table-column>

              <el-table-column
                prop="inventoryCode"
                header-align="center"
                align="center"
                sortable="custom"
                min-width="90"
                label="存货编码"
              >
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
              </el-table-column>
              <el-table-column
                prop="inventoryName"
                header-align="center"
                sortable="custom"
                min-width="90"
                label="存货名称"
              >
              </el-table-column>
              <el-table-column
                prop="inventoryWarehouse.warehouse.name"
                header-align="center"
                sortable="custom"
                min-width="90"
                label="仓库"
              >
              </el-table-column>
              <el-table-column
                prop="measurementMethod"
                header-align="center"
                sortable="custom"
                min-width="90"
                label="计量方式"
              >
                <template slot-scope="scope">
                  {{
                    $dictUtils.getDictLabel(
                      "measurement_method",
                      scope.row.measurementMethod,
                      "-"
                    )
                  }}
                </template>
              </el-table-column>
              <el-table-column
                prop="singalUnit"
                header-align="center"
                align="center"
                sortable="custom"
                min-width="110"
                label="计量单位"
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
              </el-table-column>
              <el-table-column
                prop="model"
                header-align="center"
                sortable="custom"
                min-width="90"
                label="规格型号"
              >
              </el-table-column>
              <el-table-column
                prop="inventoryWarehouse.inventoryInStock"
                header-align="center"
                align="center"
                sortable="custom"
                min-width="110"
                label="在库库存"
              >
              </el-table-column>
              <el-table-column
                prop="inventoryWarehouse.availableStock"
                header-align="center"
                align="center"
                sortable="custom"
                min-width="110"
                label="可用库存"
              >
              </el-table-column>
              <el-table-column
                prop="nventoryAttributeValue"
                header-align="center"
                align="center"
                sortable="custom"
                min-width="110"
                label="属性值"
              >
              </el-table-column>
            </el-table>
            <el-pagination
              @size-change="sizeChangeHandle"
              @current-change="currentChangeHandle"
              :current-page="pageNo"
              :page-sizes="[5, 10, 50, 100]"
              :page-size="pageSize"
              :total="total"
              layout="total, sizes, prev, pager, next, jumper"
            >
            </el-pagination>
          </el-main>
        </el-container>

        <el-aside width="200px">
          <el-tag
            :key="tag.id"
            v-for="tag in dataListAllSelections"
            closable
            :disable-transitions="false"
            @close="del(tag)"
          >
            {{ tag.inventoryName }}
          </el-tag>
        </el-aside>
      </el-container>
      <span slot="footer" class="dialog-footer">
        <el-button
          size="small"
          @click="visible = false"
          icon="el-icon-circle-close"
          >关闭</el-button
        >
        <el-button
          size="small"
          type="primary"
          icon="el-icon-circle-check"
          @click="doSubmit()"
          >确定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
import InventoryService from "@/api/basic/inventorytype/InventoryService";
import InventoryTypeService from "@/api/basic/inventorytype/InventoryTypeService";
import SelectTree from "@/components/treeSelect/treeSelect.vue";
import GridSelect from "@/components/gridSelect";
export default {
  data() {
    return {
      searchForm: {
        inventoryType: {
          id: "",
        },
        inventoryCode: "",
        inventoryName: "",
        manufactor: "",
        preStorageWarehouse: {
          id: "",
        },
        inventoryWarehouse: {
          id: "",
          warehouse: { id: "" },
        },
      },
      filterText: "",
      inventoryTypeTreeData: [],
      dataListAllSelections: [], // 所有选中的数据包含跨页数据
      dataListSelections: [],
      idKey: "id", // 标识列表数据中每一行的唯一键的名称(需要按自己的数据改一下)
      dataList: [],
      dynamicTags: [],
      pageNo: 1,
      pageSize: 10,
      total: 0,
      orders: [],
      loading: false,
      visible: false,
    };
  },
  components: {
    SelectTree,
    GridSelect,
  },
  inventoryService: null,
  InventoryTypeService: null,
  created() {
    this.inventoryService = new InventoryService();
    this.InventoryTypeService = new InventoryTypeService();
  },
  props: {
    params: {},
    selectData: {
      type: Array,
      default: () => {
        return [];
      },
    },
    limit: {
      type: Number,
      default: 999999,
    },
  },
  watch: {
    filterText(val) {
      this.$refs.inventoryTypeTree.filter(val);
    },
  },
  methods: {
    init() {
      this.visible = true;
      this.$nextTick(() => {
        this.refreshTree();
        this.resetSearch();
        this.dataListAllSelections = JSON.parse(
          JSON.stringify(this.selectData)
        );
      });
    },
    renderContent(h, { node, data, store }) {
      return (
        <span class="custom-tree-node">
          {data.type === "1" ? (
            <i class="fa fa-sitemap"></i>
          ) : (
            <i class="fa fa-users"></i>
          )}
          <span class="text">{node.label}</span>
        </span>
      );
    },
    getTemplateRow(index, row) {
      // 获取选中数据
      this.dataListSelections = [row];
      this.$nextTick(() => {
        this.changePageCoreRecordData();
      });
    },
    // 设置选中的方法
    setSelectRow() {
      if (
        !this.dataListAllSelections ||
        this.dataListAllSelections.length <= 0
      ) {
        this.$refs.userTable.clearSelection();
        return;
      }
      // 标识当前行的唯一键的名称
      // let idKey = this.idKey
      let selectAllIds = [];
      this.dataListAllSelections.forEach((row) => {
        selectAllIds.push(row.inventoryWarehouse.id);
      });
      this.$refs.userTable.clearSelection();
      for (var i = 0; i < this.dataList.length; i++) {
        if (selectAllIds.indexOf(this.dataList[i].inventoryWarehouse.id) >= 0) {
          // 设置选中，记住table组件需要使用ref="table"
          this.$refs.userTable.toggleRowSelection(this.dataList[i], true);
        }
      }
    },
    // 记忆选择核心方法
    changePageCoreRecordData() {
      // 标识当前行的唯一键的名称
      // let idKey = this.idKey
      let that = this;
      // 如果总记忆中还没有选择的数据，那么就直接取当前页选中的数据，不需要后面一系列计算
      if (this.dataListAllSelections.length <= 0) {
        this.dataListSelections.forEach((row) => {
          that.dataListAllSelections.push(row);
        });
        return;
      }
      // 总选择里面的key集合
      let selectAllIds = [];
      this.dataListAllSelections.forEach((row) => {
        selectAllIds.push(row.inventoryWarehouse.id);
      });
      let selectIds = [];
      // 获取当前页选中的id
      this.dataListSelections.forEach((row) => {
        selectIds.push(row.inventoryWarehouse.id);
        // 如果总选择里面不包含当前页选中的数据，那么就加入到总选择集合里
        if (selectAllIds.indexOf(row.inventoryWarehouse.id) < 0) {
          that.dataListAllSelections.push(row);
        }
      });
      let noSelectIds = [];
      // 得到当前页没有选中的id
      this.dataList.forEach((row) => {
        if (selectIds.indexOf(row.inventoryWarehouse.id) < 0) {
          noSelectIds.push(row.inventoryWarehouse.id);
        }
      });
      noSelectIds.forEach((id) => {
        if (selectAllIds.indexOf(id) >= 0) {
          for (let i = 0; i < that.dataListAllSelections.length; i++) {
            if (that.dataListAllSelections[i].inventoryWarehouse.id === id) {
              // 如果总选择中有未被选中的，那么就删除这条
              that.dataListAllSelections.splice(i, 1);
              break;
            }
          }
        }
      });
    },
    // 得到选中的所有数据
    getAllSelectionData() {
      // 再执行一次记忆勾选数据匹配，目的是为了在当前页操作勾选后直接获取选中数据
      this.changePageCoreRecordData();
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    del(tag) {
      this.dataListAllSelections.splice(
        this.dataListAllSelections.indexOf(tag),
        1
      );
      this.$nextTick(() => {
        this.setSelectRow();
      });
    },
    // 获取数据列表
    refreshList() {
      this.loading = true;
      this.dataListAllSelections = [];
      if (this.params && this.params.preStorageWarehouseId) {
        this.searchForm.inventoryWarehouse.warehouse.id =
          this.params.preStorageWarehouseId;
      }
      this.searchForm = Object.assign({}, this.searchForm, this.params);
      this.inventoryService
        .selectList({
          current: this.pageNo,
          size: this.pageSize,
          orders: this.orders,
          // ...this.searchForm
          "inventoryType.id": this.searchForm.inventoryType.id,
          "inventoryWarehouse.warehouse.id":
            this.searchForm.inventoryWarehouse.warehouse.id,
          inventoryName: this.searchForm.inventoryName,
          inventoryCode: this.searchForm.inventoryCode,
          manufactor: this.searchForm.manufactor,
          "preStorageWarehouse.id": this.searchForm.preStorageWarehouse.id,
          "inventoryWarehouse.id": this.searchForm.inventoryWarehouse.id,
        })
        .then(({ data }) => {
          this.dataList = data.records;
          this.total = data.total;
          this.loading = false;
        });
    },
    refreshTree() {
      this.InventoryTypeService.treeData().then(({ data }) => {
        this.inventoryTypeTreeData = data;
      });
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageNo = 1;
      this.refreshList();
      this.$nextTick(() => {
        this.changePageCoreRecordData();
      });
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageNo = val;
      this.refreshList();
      this.$nextTick(() => {
        this.changePageCoreRecordData();
      });
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val;
      this.$nextTick(() => {
        this.changePageCoreRecordData();
      });
    },
    // 排序
    sortChangeHandle(column) {
      if (column.prop === "officeDTO.name") {
        column.prop = "o.name";
      }
      if (column.prop === "companyDTO.name") {
        column.prop = "c.name";
      }
      this.orders = [];
      if (column.order != null) {
        this.orders.push({
          column: this.$utils.toLine(column.prop),
          asc: column.order === "ascending",
        });
      }
      this.refreshList();
    },
    handleNodeClick(data) {
      this.searchForm.inventoryType.id = data.id;
      this.refreshList();
    },
    resetSearch() {
      this.$refs.searchForm.resetFields();
      this.filterText = "";
      this.$refs.inventoryTypeTree.setCurrentKey(null);
      this.refreshList();
    },
    doSubmit() {
      if (this.limit < this.dataListAllSelections.length) {
        this.$message.error(`你最多只能选择${this.limit}条数据`);
        return;
      }
      this.visible = false;
      this.$emit("doSubmit", this.dataListAllSelections);
    },
  },
};
</script>
<style lang="scss">
.org {
  height: 100%;

  .el-card__header {
    padding: 10px;
  }

  .el-card__body {
    padding: 10px;
    max-height: 520px;
    overflow: auto;
  }
}

.userDialog {
  .el-dialog__body {
    padding: 10px 0px 0px 10px;
    color: #606266;
    font-size: 14px;
    word-break: break-all;
  }

  .el-main {
    padding: 20px 20px 5px 20px;

    .el-pagination {
      margin-top: 5px;
    }
  }
}
</style>
