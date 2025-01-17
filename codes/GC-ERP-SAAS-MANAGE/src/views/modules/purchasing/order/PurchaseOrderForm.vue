<template>
  <div>
    <el-dialog
      :title="title"
      :close-on-click-modal="false"
      v-dialogDrag
      :visible.sync="visible"
      width="80%"
    >
      <el-form
        :model="inputForm"
        size="small"
        ref="inputForm"
        v-loading="loading"
        :class="method === 'view' ? 'readonly' : ''"
        :disabled="method === 'view'"
        label-width="120px"
      >
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="采购订单编号" prop="purCode" :rules="[]">
              <el-input
                v-model="inputForm.purCode"
                placeholder="自动生成"
                :disabled="true"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="采购合同" prop="purContract.id" :rules="[]">
              <GridSelect
                title="选择采购合同"
                labelName="purchaseCode"
                labelValue="id"
                :value="inputForm.purContract.id"
                @valueChange="contractChange"
                :limit="1"
                @getValue="
                  (value) => {
                    inputForm.purContract.id = value;
                  }
                "
                :columns="[
                  {
                    prop: 'purchaseCode',
                    label: '合同编号',
                  },
                  {
                    prop: 'supplier.supplierName',
                    label: '供应商',
                  },
                  {
                    prop: 'purchaseData',
                    label: '合同日期',
                  },
                ]"
                :searchs="[
                  {
                    prop: 'purchaseCode',
                    label: '合同编号',
                  },
                ]"
                dataListUrl="/purchasing/purchasecontract/purchaseContract/list1"
                queryEntityUrl="/purchasing/purchasecontract/purchaseContract/queryById"
              >
              </GridSelect>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="请购单" prop="purRequisition.id" :rules="[]">
              <GridSelect
                title="选择请购单"
                labelName="requisitionNumber"
                labelValue="id"
                :value="inputForm.purRequisition.id"
                @valueChanges="requisitionChange"
                :limit="99"
                @getValue="
                  (value) => {
                    inputForm.purRequisition.id = value;
                  }
                "
                :columns="[
                  {
                    prop: 'requisitionNumber',
                    label: '请购单编号',
                  },
                  {
                    prop: 'requisitionUser.name',
                    label: '请购人',
                  },
                  {
                    prop: 'requisitionDep.name',
                    label: '请购部门',
                  },
                  {
                    prop: 'requisitionData',
                    label: '请购时间',
                  },
                ]"
                :searchs="[
                  {
                    prop: 'requisitionNumber',
                    label: '请购单编号',
                  },
                ]"
                dataListUrl="/purchasing/requisition/requisition/list1"
                queryEntityUrl="/purchasing/requisition/requisition/queryById"
              >
              </GridSelect>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="供应商"
              prop="purSupplier.id"
              :rules="[
                { required: true, message: '供应商不能为空', trigger: 'blur' },
              ]"
            >
              <GridSelect
                title="选择供应商"
                labelName="supplierName"
                labelValue="id"
                :value="inputForm.purSupplier.id"
                :limit="1"
                @getValue="
                  (value) => {
                    inputForm.purSupplier.id = value;
                  }
                "
                :columns="[
                  {
                    prop: 'supplierName',
                    label: '供应商名称',
                  },
                  {
                    prop: 'concat',
                    label: '联系人',
                  },
                  {
                    prop: 'phone',
                    label: '电话',
                  },
                  {
                    prop: 'address',
                    label: '地址',
                  },
                  {
                    prop: 'supplierLevel',
                    label: '供应商等级',
                  },
                ]"
                :searchs="[
                  {
                    prop: 'supplierName',
                    label: '供应商名称',
                  },
                ]"
                dataListUrl="/supplier/inventoryset/supplier/list"
                queryEntityUrl="/supplier/inventoryset/supplier/queryById"
                :disabled="supplierShow"
              >
              </GridSelect>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label-width="0">
              <el-tabs v-model="purchaseOrderTab">
                <el-tab-pane label="采购订单详情">
                  <el-button
                    size="small"
                    @click="addPurchaseOrderDetailRow"
                    type="primary"
                    >新增</el-button
                  >
                  <el-table
                    class="table"
                    size="small"
                    :data="
                      inputForm.purchaseOrderDetailDTOList.filter(function (
                        item
                      ) {
                        return item.delFlag !== '1';
                      })
                    "
                    style="width: 100%"
                  >
                    <el-table-column
                      prop="inventory.inventoryName"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="存货名称"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="inventory.inventoryCode"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="存货编码"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="inventory.model"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="规格型号"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="inventory.nventoryAttributeValue"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="存货属性值"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="inventory.singalUnit"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="计量单位"
                    >
                      <template slot-scope="scope">
                        {{
                          $dictUtils.getDictLabel(
                            "sx_metering_unit",
                            scope.row.inventory.singalUnit,
                            "-"
                          )
                        }}
                      </template>
                    </el-table-column>
                    <el-table-column
                      prop="inventory.inventoryAttribute"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="可用库存"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="purOrderNum"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="采购数量"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="supplyDate"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="供货时间"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="unitPrice"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="含税单价(元)"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="totalPrice"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="总价(含税)(元)"
                    >
                    </el-table-column>
                    <!-- <el-table-column v-for="item in attrList" :key="item.value" :label="item.chineseName"
                      :prop="item.englishName" header-align="center" align="center" show-overflow-tooltip>
                    </el-table-column> -->
                    <el-table-column
                      prop="orderAttribute"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="采购备注"
                    >
                    </el-table-column>
                    <el-table-column fixed="right" label="操作" width="150">
                      <template slot-scope="scope">
                        <el-button
                          @click="viewPurchaseOrderDetailRow(scope.row)"
                          type="text"
                          size="small"
                          >查看
                        </el-button>
                        <el-button
                          @click="editPurchaseOrderDetailRow(scope.row)"
                          type="text"
                          size="small"
                          >编辑
                        </el-button>
                        <el-button
                          @click="delPurchaseOrderDetailRow(scope.row)"
                          type="text"
                          size="small"
                          >删除</el-button
                        >
                      </template>
                    </el-table-column>
                  </el-table>
                </el-tab-pane>
              </el-tabs>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="visible = false">关闭</el-button>
        <el-button
          size="small"
          type="primary"
          v-if="method != 'view'"
          @click="doSubmit()"
          v-noMoreClick
          >确定</el-button
        >
        <!-- <el-button size="small" type="primary" v-if="method === 'view'" @click="print()">打印</el-button> -->
      </span>
    </el-dialog>
    <PurchaseOrderDetailForm
      ref="purchaseOrderDetailForm"
      @addRow="savePurchaseOrderDetailRow(arguments)"
    >
    </PurchaseOrderDetailForm>
    <PrintView ref="printView"></PrintView>
  </div>
</template>

<script>
import PurchaseOrderDetailForm from "./PurchaseOrderDetailForm";
import PrintView from "@/views/modules/wh/prints/PrintView";
import GridSelect from "@/components/gridSelect";
import PurchaseOrderService from "@/api/purchasing/order/PurchaseOrderService";
import OrderAttributeService from "@/api/basic/orderattribute/OrderAttributeService";
import RequisitionService from "@/api/purchasing/requisition/RequisitionService";
export default {
  data() {
    return {
      supplierShow: false,
      title: "",
      method: "",
      visible: false,
      loading: false,
      purchaseOrderTab: "0",
      inputForm: {
        id: "",
        purchaseOrderDetailDTOList: [],
        purCode: "",
        purContract: {
          id: "",
        },
        purRequisition: {
          id: "",
        },
        purSupplier: {
          id: "",
        },
        procInsId: "",
        procInsResult: "",
        purState: "",
      },
      attrList: [],
    };
  },
  components: {
    GridSelect,
    PurchaseOrderDetailForm,
    PrintView,
  },
  purchaseOrderService: null,
  orderAttributeService: null,
  requisitionService: null,
  created() {
    this.purchaseOrderService = new PurchaseOrderService();
    this.orderAttributeService = new OrderAttributeService();
    this.requisitionService = new RequisitionService();
  },
  methods: {
    init(method, id) {
      this.method = method;
      if (method === "add") {
        this.title = `新建采购订单`;
      } else if (method === "edit") {
        this.title = "修改采购订单";
      } else if (method === "view") {
        this.title = "查看采购订单";
      }
      this.visible = true;
      this.loading = false;
      this.$nextTick(() => {
        this.$refs.inputForm.resetFields();
        this.getAttrList();
        this.inputForm.id = id;
        this.purchaseOrderTab = "0";
        this.inputForm.purchaseOrderDetailDTOList = [];
        if (method === "edit" || method === "view") {
          // 修改或者查看
          this.loading = true;
          this.purchaseOrderService
            .queryById(this.inputForm.id)
            .then(({ data }) => {
              this.inputForm = this.recover(this.inputForm, data);
              this.loading = false;
            });
        }
      });
    },
    savePurchaseOrderDetailRow(child) {
      if (child[0] === "") {
        this.inputForm.purchaseOrderDetailDTOList.push(child[1]);
      } else {
        this.inputForm.purchaseOrderDetailDTOList.forEach((item, index) => {
          if (item === child[0]) {
            this.inputForm.purchaseOrderDetailDTOList.splice(
              index,
              1,
              child[1]
            );
          }
        });
      }
    },
    addPurchaseOrderDetailRow(child) {
      this.$refs.purchaseOrderDetailForm.init("add");
    },
    viewPurchaseOrderDetailRow(child) {
      this.$refs.purchaseOrderDetailForm.init("view", child);
    },
    editPurchaseOrderDetailRow(child) {
      this.$refs.purchaseOrderDetailForm.init("edit", child);
    },
    delPurchaseOrderDetailRow(child) {
      this.inputForm.purchaseOrderDetailDTOList.forEach((item, index) => {
        if (item === child && item.id === "") {
          this.inputForm.purchaseOrderDetailDTOList.splice(index, 1);
        } else if (item === child) {
          item.delFlag = "1";
          this.inputForm.purchaseOrderDetailDTOList.splice(index, 1, item);
        }
      });
    },
    // 表单提交
    doSubmit() {
      this.$refs["inputForm"].validate((valid) => {
        if (valid) {
          this.loading = true;
          this.purchaseOrderService
            .save(this.inputForm)
            .then(({ data }) => {
              this.visible = false;
              this.$message.success(data);
              this.$emit("refreshDataList");
              this.loading = false;
            })
            .catch(() => {
              this.loading = false;
            });
        }
      });
    },
    requisitionChange(data) {
      if (data !== undefined && data.length > 0) {
        let purchaseOrderDetailDTOList = [];
        for (let i in data) {
          this.requisitionService.queryById(data[i].id).then(({ data }) => {
            data.requisitionDetailDTOList.forEach((item) => {
              this.$set(item, "purOrderNum", item.inventoryNum);
              this.$set(item, "purUsedNum", 0);
              this.$set(item, "purUnusedNum", item.inventoryNum);
              this.$set(item, "unitPrice", item.inventory.taxincludedPrice);
              this.$set(
                item,
                "totalPrice",
                parseFloat(
                  item.inventory.taxincludedPrice * item.inventoryNum
                ).toFixed(2)
              );
              purchaseOrderDetailDTOList.push(item);
            });
          });
        }
        this.inputForm.purchaseOrderDetailDTOList = purchaseOrderDetailDTOList;
      }
    },
    getAttrList() {
      let _this = this;
      _this.orderAttributeService.list({}).then(({ data }) => {
        _this.attrList = data.records;
      });
    },
    contractChange(data) {
      if (data != null && data != "" && data != undefined) {
        this.inputForm.purSupplier.id = data.supplier.id;
        this.supplierShow = true;
      } else {
        this.supplierShow = false;
      }
    },
    // 打印
    print() {
      var that = this;
      that.purchaseOrderService
        .print(that.inputForm.id)
        .then(({ data }) => {
          that.visible = false;
          that.$refs.printView.init(data);
          that.loading = false;
        })
        .catch(() => {
          that.loading = false;
        });
    },
  },
};
</script>
