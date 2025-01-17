<template>
  <div>
    <el-dialog
      :title="title"
      :close-on-click-modal="false"
      v-dialogDrag
      width="80%"
      :visible.sync="visible"
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
          <el-col :span="8">
            <el-form-item
              label="发货编号"
              :prop="method != 'add' ? '' : 'shippingListNo'"
              :rules="[]"
            >
              <el-input
                v-model="inputForm.shippingListNo"
                placeholder="自动生成"
                :disabled="true"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="产品出库单"
              prop="productDelivery.id"
              :rules="[]"
            >
              <GridSelect
                title="选择产品出库单"
                labelName="deliveryOrderNo"
                labelValue="id"
                :value="inputForm.productDelivery.id"
                :limit="10"
                @valueChanges="selectProductDeliveryChange"
                @getValue="
                  (value) => {
                    inputForm.productDelivery.id = value;
                  }
                "
                :columns="[
                  {
                    prop: 'deliveryOrderNo',
                    label: '出库单编号',
                  },
                  {
                    prop: 'salesorder.salesorderCode',
                    label: '销售订单',
                  },
                  {
                    prop: 'salesorder.customer.customerName',
                    label: '客户名称',
                  },
                  {
                    prop: 'salesorder.createDate',
                    label: '销售日期',
                  },
                ]"
                :searchs="[
                  {
                    prop: 'deliveryOrderNo',
                    label: '出库单编号',
                  },
                  {
                    prop: 'salesorder.id',
                    label: '销售订单',
                  },
                  {
                    prop: 'issueType',
                    label: '出库类型',
                    value: '1',
                    show: 1,
                  },
                ]"
                dataListUrl="/wh/productdelivery/productdelivery/list"
                queryEntityUrl="/wh/productdelivery/productdelivery/queryById"
              >
              </GridSelect>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="客户名称" prop="customer.id" :rules="[]">
              <GridSelect
                title="选择客户名称"
                labelName="customerName"
                labelValue="id"
                @valueChange="selectCustomerChange"
                :value="inputForm.customer.id"
                :limit="1"
                @getValue="
                  (value) => {
                    inputForm.customer.id = value;
                  }
                "
                :columns="[
                  {
                    prop: 'id',
                    label: 'id',
                  },
                  {
                    prop: 'customerName',
                    label: '客户名称',
                  },
                  {
                    prop: 'contacts',
                    label: '联系人',
                  },
                  {
                    prop: 'contactsPhone',
                    label: '联系人电话',
                  },
                  {
                    prop: 'contactsAddr',
                    label: '联系人地址',
                  },
                ]"
                :searchs="[
                  {
                    prop: 'id',
                    label: 'id',
                  },
                  {
                    prop: 'customerName',
                    label: '客户名称',
                  },
                  {
                    prop: 'contacts',
                    label: '联系人',
                  },
                  {
                    prop: 'contactsPhone',
                    label: '联系人电话',
                  },
                  {
                    prop: 'contactsAddr',
                    label: '联系人地址',
                  },
                ]"
                dataListUrl="/basic/customer/customer/list"
                queryEntityUrl="/basic/customer/customer/queryById"
              >
              </GridSelect>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="8">
            <el-form-item label="更换收货人信息" prop="receivingAddr.id" :rules="[
                 ]">
              <GridSelect title="选择收货人" labelName='receivingAddr' labelValue='id'
              @valueChange='selectReceivingAddrChange'
              :value="inputForm.receivingAddr.id"
                :limit="1" @getValue='(value) => {inputForm.receivingAddr.id=value}' :columns="[

            {
              prop: 'contacts',
              label: '收货人'
            },
            {
              prop: 'contactsAddr',
              label: '收货地址'
            },

            ]" :searchs="[
            {
              prop: 'customerId',
              label: 'customerId',
              value:inputForm.customer.id,
              show:1
            },
            {
              prop: 'contacts',
              label: '收货人'
            },

            ]" dataListUrl="/basic/customer/customer/queryAddrById"
                queryEntityUrl="/basic/customer/customer/queryAddrByIds">
              </GridSelect>
            </el-form-item>
          </el-col> -->
          <el-col :span="8">
            <el-form-item label="收货人" prop="contacts" :rules="[]">
              <el-input
                v-model="inputForm.contacts"
                placeholder="请填写联系人"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="收货人电话" prop="contactsPhone" :rules="[]">
              <el-input
                v-model="inputForm.contactsPhone"
                placeholder="请填写联系人电话"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="收货人地址" prop="contactsAddr" :rules="[]">
              <el-input
                v-model="inputForm.contactsAddr"
                placeholder="请填写联系人地址"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="承运单位名称" prop="nameOfCarrier" :rules="[]">
              <el-input
                v-model="inputForm.nameOfCarrier"
                placeholder="请填写承运单位名称"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="物流号" prop="logisticsNo" :rules="[]">
              <el-input
                v-model="inputForm.logisticsNo"
                placeholder="请填写物流号"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="发货车牌" prop="freight" :rules="[]">
              <el-input
                v-model="inputForm.licensePlate"
                placeholder="请填写发货车牌"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="司机名字" prop="freight" :rules="[]">
              <el-input
                v-model="inputForm.driverName"
                placeholder="请填写司机名字"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="司机电话" prop="freight" :rules="[]">
              <el-input
                v-model="inputForm.driverTel"
                placeholder="请填写司机电话"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="运费(元)" prop="freight" :rules="[]">
              <el-input
                v-model="inputForm.freight"
                placeholder="请填写运费"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="发货人" prop="preparer.id" :rules="[]">
              <user-select
                :limit="1"
                :value="inputForm.preparer.id"
                @getValue="
                  (value) => {
                    inputForm.preparer.id = value;
                  }
                "
              ></user-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="复核人" prop="reviewed.id" :rules="[]">
              <user-select
                :limit="1"
                :value="inputForm.reviewed.id"
                @getValue="
                  (value) => {
                    inputForm.reviewed.id = value;
                  }
                "
              ></user-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item prop="companyDTO.id" :rules="[]" label="公司">
              <SelectTree
                ref="companyTree"
                :props="{
                  value: 'id', // ID字段名
                  label: 'name', // 显示名称
                  children: 'children', // 子级字段名
                }"
                :url="`/sys/office/treeData?type=1`"
                :value="inputForm.companyDTO.id"
                :clearable="true"
                :accordion="true"
                @getValue="
                  (value) => {
                    inputForm.companyDTO.id = value;
                  }
                "
              />
            </el-form-item>
          </el-col>
          <!-- <el-col :span="12">
            <el-form-item label="发货状态" prop="shipmentStatus" :rules="[
                 ]">
              <el-select v-model="inputForm.shipmentStatus" placeholder="请选择" style="width: 100%;">
                <el-option v-for="item in $dictUtils.getDictList('shipment_status')" :key="item.value"
                  :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col> -->
          <el-col :span="24">
            <el-form-item label-width="0">
              <el-tabs v-model="shippinglistTab">
                <el-tab-pane label="发货清单详情">
                  <!--//通过按钮来调用-->

                  <!-- <el-button size="small" @click="addShippinglistdetailsRow" type="primary" v-if="inputForm.productDelivery.id == ''">新增</el-button> -->
                  <el-table
                    class="table"
                    size="small"
                    :data="
                      inputForm.shippinglistdetailsDTOList.filter(function (
                        item
                      ) {
                        return item.delFlag !== '1';
                      })
                    "
                    style="width: 100%"
                  >
                    <!-- <el-table-column
                    prop="shippinglist.id"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="发货清单">
                  </el-table-column> -->
                    <el-table-column
                      prop="inventory.inventoryName"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="存货名称"
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

                    <!--
                    <el-table-column v-for="item in attrList" :key="item.value" :label="item.chineseName"
                      :prop="item.englishName" header-align="center" align="center" show-overflow-tooltip>
                    </el-table-column> -->
                    <el-table-column
                      prop="inventory.nventoryAttributeValue"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="存货属性"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="inventory.singalUnit"
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
                      prop="outboundQuantity"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="当前出库数量"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="exWarehouse.name"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="出库仓库"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="volumenumber"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="卷数"
                    >
                    </el-table-column>
                    <!-- <el-table-column prop="other" header-align="center" align="center"
                      show-overflow-tooltip label="订单属性">
                    </el-table-column> -->
                    <el-table-column
                      v-for="item in attrList"
                      :key="item.value"
                      :label="item.chineseName"
                      :prop="item.englishName"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                    >
                    </el-table-column>
                    <el-table-column
                      prop="remark"
                      header-align="center"
                      align="center"
                      show-overflow-tooltip
                      label="备注"
                    >
                    </el-table-column>
                    <!-- <el-table-column fixed="right" label="操作" width="150">
                      <template slot-scope="scope" v-if="inputForm.productDelivery.id == ''">
                        <el-button @click="viewShippinglistdetailsRow(scope.row)" type="text" size="small">查看
                        </el-button>
                        <el-button @click="editShippinglistdetailsRow(scope.row)" type="text" size="small">编辑
                        </el-button>
                        <el-button @click="delShippinglistdetailsRow(scope.row)" type="text" size="small">删除</el-button>
                      </template>
                    </el-table-column> -->
                  </el-table>
                </el-tab-pane>
              </el-tabs>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!-- <el-button type="primary" @click="dayin()">打印</el-button> -->
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
      </span>
    </el-dialog>
    <ShippinglistdetailsForm
      ref="shippinglistdetailsForm"
      @addRow="saveShippinglistdetailsRow(arguments)"
    >
    </ShippinglistdetailsForm>
    <PrintView ref="printView"></PrintView>
  </div>
</template>

<script>
import ShippinglistdetailsForm from "./ShippinglistdetailsForm";
import PrintView from "../prints/PrintView";
import GridSelect from "@/components/gridSelect";
import UserSelect from "@/components/userSelect";
import SelectTree from "@/components/treeSelect/treeSelect.vue";
import ShippinglistService from "@/api/wh/shippinglist/ShippinglistService";
import ProductdeliveryService from "@/api/wh/productdelivery/ProductdeliveryService";
import OrderAttributeService from "@/api/basic/orderattribute/OrderAttributeService";
export default {
  data() {
    return {
      title: "",
      method: "",
      visible: false,
      loading: false,
      shippinglistTab: "0",
      attrList: [],
      orderAttrId: "",
      /* printObj: {
                 id: 'printMe',//id
                 popTitle: "发货清单",//自定义设置标题
                 extraHead:'打印'
               }, */
      inputForm: {
        id: "",
        shippinglistdetailsDTOList: [],
        shippingListNo: "",
        salesorderId: "",
        outboundQuantity: "",
        volumenumber: "",
        productDelivery: {
          id: "",
        },
        customer: {
          id: "",
          customerName: "",
          contacts: "",
          contactsPhone: "",
          contactsAddr: "",
          orderAttr: {
            id: "",
          },
        },
        companyDTO: {
          // 归属公司
          id: "",
          name: "",
        },

        receivingAddr: {
          id: "",
        },
        customerName: "",
        customerId: "",
        contacts: "",
        contactsPhone: "",
        contactsAddr: "",
        nameOfCarrier: "",
        logisticsNo: "",
        freight: "",
        preparer: {
          id: "",
        },
        reviewed: {
          id: "",
        },
        deliveryTime: "",
        shipmentStatus: "",
      },
    };
  },
  components: {
    GridSelect,
    UserSelect,
    SelectTree,
    ShippinglistdetailsForm,
    PrintView,
  },
  shippinglistService: null,
  ProductdeliveryService: null,
  orderAttributeService: null,
  created() {
    this.inputForm.deliveryTime = this.$dateUtils.formatTime(new Date());
    this.shippinglistService = new ShippinglistService();
    this.productdeliveryService = new ProductdeliveryService();
    this.orderAttributeService = new OrderAttributeService();
  },
  methods: {
    init(method, id) {
      this.method = method;
      if (method === "add") {
        this.title = `新建发货清单`;
      } else if (method === "edit") {
        this.title = "修改发货清单";
      } else if (method === "view") {
        this.title = "查看发货清单";
      }
      this.visible = true;
      this.loading = false;
      this.$nextTick(() => {
        this.$refs.inputForm.resetFields();
        this.inputForm.id = id;
        this.shippinglistTab = "0";
        this.inputForm.shippinglistdetailsDTOList = [];
        if (method === "edit" || method === "view") {
          // 修改或者查看
          this.loading = true;
          this.shippinglistService
            .queryById(this.inputForm.id)
            .then(({ data }) => {
              this.inputForm = this.recover(this.inputForm, data);

              if (data.customer && data.customer.orderAttr) {
                //订单属性回显
                this.orderAttrId = data.customer.orderAttr.id;
                //根据所选客户的订单属性来自动填充详情
                this.orderAttributeService
                  .queryById1(data.customer.orderAttr.id)
                  .then(({ data }) => {
                    this.attrList = data;
                  });
              }

              let shippinglistdetailsDTOList = data.shippinglistdetailsDTOList;
              for (var i = 0; i < shippinglistdetailsDTOList.length; i++) {
                let orderAttributeArray = eval(
                  shippinglistdetailsDTOList[i].orderAttribute
                );
                if (orderAttributeArray) {
                  for (var j = 0; j < orderAttributeArray.length; j++) {
                    var json = eval(orderAttributeArray[j]);
                    for (var key in json) {
                      /*    debugger */
                      shippinglistdetailsDTOList[i][key] = json[key];
                    }
                  }
                }
              }

              this.loading = false;
            });
        }
      });
    },
    saveShippinglistdetailsRow(child) {
      if (child[0] === "") {
        this.inputForm.shippinglistdetailsDTOList.push(child[1]);
      } else {
        this.inputForm.shippinglistdetailsDTOList.forEach((item, index) => {
          if (item === child[0]) {
            this.inputForm.shippinglistdetailsDTOList.splice(
              index,
              1,
              child[1]
            );
          }
        });
      }
    },
    addShippinglistdetailsRow(child) {
      this.$refs.shippinglistdetailsForm.init("add", {}, this.orderAttrId);
    },
    viewShippinglistdetailsRow(child) {
      this.$refs.shippinglistdetailsForm.init("view", child, this.orderAttrId);
    },
    editShippinglistdetailsRow(child) {
      this.$refs.shippinglistdetailsForm.init("edit", child, this.orderAttrId);
    },
    delShippinglistdetailsRow(child) {
      this.inputForm.shippinglistdetailsDTOList.forEach((item, index) => {
        if (item === child && item.id === "") {
          this.inputForm.shippinglistdetailsDTOList.splice(index, 1);
        } else if (item === child) {
          item.delFlag = "1";
          this.inputForm.shippinglistdetailsDTOList.splice(index, 1, item);
        }
      });
    },
    // 表单提交
    doSubmit() {
      this.$refs["inputForm"].validate((valid) => {
        if (valid) {
          this.loading = true;
          this.shippinglistService
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
    // 打印
    dayin() {
      var that = this;
      /* debugger */
      var shippinglistdetailsDTOList =
        that.inputForm.shippinglistdetailsDTOList;
      that.loading = true;
      that.shippinglistService
        .dayin(that.inputForm.id)
        .then(({ data }) => {
          that.visible = false;
          for (var i = 0; i < shippinglistdetailsDTOList.length; i++) {
            var orderAttr = eval(shippinglistdetailsDTOList[i].orderAttribute);
            if (orderAttr) {
              for (var a = 0; a < orderAttr.length; a++) {
                for (var j = 0; j < that.attrList.length; j++) {
                  var attr = that.attrList[j];
                  for (var key in orderAttr[a]) {
                    if (attr.englishName == key) {
                      data.schedule[attr.chineseName] = key;
                      data.list[i][attr.englishName] = orderAttr[a][key];
                      /* data.schedule[attr.chineseName] = orderAttr[a][key] */
                    }
                  }
                }
              }
            }
          }
          /* that.attrList.forEach(function(item){
          debugger
          data.schedule[item.chineseName]=item.englishName
          }) */

          that.$refs.printView.init(data);
          that.loading = false;
        })
        .catch(() => {
          that.loading = false;
        });
    },
    selectCustomerChange(data) {
      this.inputForm.customerName = data.customerName;
      this.inputForm.contacts = data.contacts;
      this.inputForm.customerId = data.id;
      this.inputForm.contactsPhone = data.contactsPhone;
      this.inputForm.contactsAddr = data.contactsAddr;
    },
    selectReceivingAddrChange(data) {
      this.inputForm.contacts = data.contacts;
      this.inputForm.contactsPhone = data.contactsPhone;
      this.inputForm.contactsAddr = data.contactsAddr;
    },
    selectProductDeliveryChange(data, va) {
      this.$refs.inputForm.resetFields();
      this.inputForm.shippinglistdetailsDTOList = [];

      let dataList = data;
      var custome = "";
      for (var i = 0; i < dataList.length; i++) {
        if (custome != "" && custome != dataList[i].salesorder.customer.id) {
          this.$message.error("销售出库时只能选择同一个客户的订单");
          va.dataListSelections = [];
          va.$nextTick(() => {
            va.resetSearch();
          });
          return;
        }
        custome = dataList[i].salesorder.customer.id;
      }
      this.inputForm.customer = data[0].salesorder.customer;
      this.inputForm.customerName = data[0].salesorder.customer.customerName;
      this.inputForm.contacts = data[0].salesorder.customer.contacts;
      /*        this.inputForm.customerId = data[0].salesorder.customer.id */
      this.inputForm.contactsPhone = data[0].salesorder.customer.contactsPhone;
      this.inputForm.contactsAddr = data[0].salesorder.customer.contactsAddr;
      this.attrList = [];
      // this.orderAttrId =data[0].salesorder.customer.orderAttr.id
      //根据所选客户的订单属性来自动填充详情
      this.orderAttributeService
        .queryById1(data[0].salesorder.customer.orderAttr.id)
        .then(({ data }) => {
          this.attrList = data;
          for (var i = 0; i < dataList.length; i++) {
            this.productdeliveryService
              .queryById(dataList[i].id)
              .then(({ data }) => {
                for (
                  var j = 0;
                  j < data.productdeliverydetailsDTOList.length;
                  j++
                ) {
                  /* debugger */
                  let shippinglistdetails = {};
                  shippinglistdetails.inventory = {};
                  shippinglistdetails.salesorderdetails = {};
                  this.inputForm.salesorderId =
                    data.productdeliverydetailsDTOList[
                      j
                    ].salesorderdetails.salesorder.id;
                  shippinglistdetails.inventory =
                    data.productdeliverydetailsDTOList[
                      j
                    ].salesorderdetails.inventory;
                  shippinglistdetails["outboundQuantity"] =
                    data.productdeliverydetailsDTOList[j].outboundQuantity;
                  /* shippinglistdetails['outboundQuantity'] = data.productdeliverydetailsDTOList[j].salesorderdetails.salesorderNum */
                  shippinglistdetails.exWarehouse = {};
                  shippinglistdetails.exWarehouse["id"] =
                    data.productdeliverydetailsDTOList[
                      j
                    ].salesorderdetails.inventory.inventoryWarehouse.warehouse.id;
                  shippinglistdetails.exWarehouse["name"] =
                    data.productdeliverydetailsDTOList[
                      j
                    ].salesorderdetails.inventory.inventoryWarehouse.warehouse.name;
                  let orderAttributeArray = eval(
                    data.productdeliverydetailsDTOList[j].salesorderdetails
                      .orderAttribute
                  );
                  shippinglistdetails.orderAttribute =
                    data.productdeliverydetailsDTOList[
                      j
                    ].salesorderdetails.orderAttribute;
                  shippinglistdetails["volumenumber"] =
                    data.productdeliverydetailsDTOList[j].volumenumber;
                  /* shippinglistdetails['orderAttribute'] =orderAttributeArray */
                  let otherList = [];
                  for (var n = 0; n < orderAttributeArray.length; n++) {
                    var json = eval(orderAttributeArray[n]);

                    for (var key in json) {
                      shippinglistdetails[key] = json[key];

                      /* for(var index in this.attrList){

                        if(key==this.attrList[index].englishName){
                          this.attrList[index].value=json[key]
                          debugger
                          otherList.push(this.attrList[index].englishName+":"+json[key])

                        }
                      } */
                      // shippinglistdetails[key] = json[key]
                    }
                  }
                  /* shippinglistdetails.other=otherList.join(";") */
                  /* shippinglistdetails.orderAttribute=otherList.join(",") */
                  /* shippinglistdetails.salesorderdetails['orderAttribute'] = data.productdeliverydetailsDTOList[j].salesorderdetails.inventory.inventoryWarehouse.warehouse.name */
                  /* shippinglistdetails.exWarehouse['id'] = '' */

                  this.inputForm.shippinglistdetailsDTOList.push(
                    shippinglistdetails
                  );
                }
              });
          }
        });
    },
  },
};
</script>
