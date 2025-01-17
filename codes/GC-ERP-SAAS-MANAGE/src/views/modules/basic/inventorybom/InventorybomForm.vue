<template>
  <div>
    <el-dialog :title="title" :close-on-click-modal="false" v-dialogDrag :visible.sync="visible">
      <el-form :model="inputForm" size="small" ref="inputForm" v-loading="loading"
        :class="method==='view'?'readonly':''" :disabled="method==='view'" label-width="120px" >
        <el-row :gutter="15">
          <el-col :span="12">
              <el-form-item label="存货" prop="inventory.id" :rules="[
                    {required: true, message:'请选择存货', trigger:'blur'}
                   ]">
                   <inventory-select :limit='1' :value="inputForm.inventory.id"
                   :params="{}"
                @getValue='(value) => {inputForm.inventory.id=value}' @changeValue="getExitsBoms">
                </inventory-select>
             </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="BOM等级" prop="sort" :rules="[
                  {required: true, message:'BOM等级不能为空', trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.sort" placeholder="请填写BOM等级"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="复制已有BOM" prop="exitsBom">
              <el-select id="exitsBom" v-model="inputForm.exitsBom" placeholder="请选择" style="width: 100%;display:block;" filterable
                popper-class="eloption" :popper-append-to-body="true"
                @change="addBOMList">
                <el-option value=''>--请选择--</el-option>
                <el-option v-for="item in exitsBomList" :key="item.value"
                  :label="'存货名称:'+item.inventory.inventoryName+'     存货编码:'+item.inventory.inventoryCode
                  +'     BOM等级:'+item.sort+'  规格型号:'+item.inventory.model+'   存货属性值'+item.inventory.nventoryAttributeValue" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark" :rules="[
                 ]">
              <el-input v-model="inputForm.remark" placeholder="请填写备注"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label-width="0">
              <el-tabs v-model="inventorybomTab">
                <el-tab-pane label="物料清单详情">
                  <el-button size="small" @click="addInventorybomDetailRow" type="primary">新增</el-button>
                  <el-table class="table" size="small"
                    :data="inputForm.inventorybomDetailDTOList.filter(function(item){ return item.delFlag !== '1'})"
                    style="width: 100%">
                    <el-table-column prop="inventory.inventoryCode" header-align="center" align="center"
                      show-overflow-tooltip label="存货编码">
                    </el-table-column>
                    <el-table-column prop="inventory.inventoryName" header-align="center" align="center"
                      show-overflow-tooltip label="存货名称">
                    </el-table-column>
                    <el-table-column prop="inventory.model" header-align="center" align="center" show-overflow-tooltip
                      label="规格型号">
                    </el-table-column>
                    <el-table-column prop="inventory.nventoryAttributeValue" header-align="center" align="center"
                      show-overflow-tooltip label="存货属性值">
                    </el-table-column>
                    <el-table-column prop="inventory.singalUnit" header-align="center" align="center"
                      show-overflow-tooltip label="计量单位">
                      <template slot-scope="scope">
                        {{ $dictUtils.getDictLabel("sx_metering_unit", scope.row.inventory.singalUnit, '-') }}
                      </template>
                    </el-table-column>
                    <el-table-column prop="num" header-align="center" align="center" show-overflow-tooltip label="所需数量">
                    </el-table-column>
                    <el-table-column prop="remark" header-align="center" align="center" show-overflow-tooltip
                      label="备注">
                    </el-table-column>
                    <el-table-column fixed="right" label="操作" width="150">
                      <template slot-scope="scope">
                        <el-button @click="viewInventorybomDetailRow(scope.row)" type="text" size="small">查看</el-button>
                        <el-button @click="editInventorybomDetailRow(scope.row)" type="text" size="small">编辑</el-button>
                        <el-button @click="delInventorybomDetailRow(scope.row)" type="text" size="small">删除</el-button>
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
        <el-button size="small" type="primary" v-if="method != 'view'" @click="doSubmit()" v-noMoreClick>确定</el-button>
      </span>
    </el-dialog>
    <InventorybomDetailForm ref="inventorybomDetailForm" @addRow="saveInventorybomDetailRow(arguments)">
    </InventorybomDetailForm>
  </div>
</template>

<script>
  import InventorybomDetailForm from './InventorybomDetailForm'
  import GridSelect from '@/components/gridSelect'
  import InventorySelect from '@/components/inventorySelect'
  import InventorybomService from '@/api/basic/inventorybom/InventorybomService'
  export default {
    data() {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        inventorybomTab: '0',
        exitsBomList: [],
        inputForm: {
          id: '',
          inventorybomDetailDTOList: [],
          inventory: {
            id: ''
          },
          sort: '',
          exitsBom: '',
          remark: ''
        }
      }
    },
    components: {
      GridSelect,
      InventorybomDetailForm,
      InventorySelect
    },
    inventorybomService: null,
    created() {
      this.inventorybomService = new InventorybomService()
    },
    methods: {
      init(method, id) {
        this.method = method
        if (method === 'add') {
          this.title = `新建物料清单`
        } else if (method === 'edit') {
          this.title = '修改物料清单'
        } else if (method === 'view') {
          this.title = '查看物料清单'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.inputForm.id = id
          this.inventorybomTab = '0'
          this.inputForm.inventorybomDetailDTOList = []
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.inventorybomService.queryById(this.inputForm.id).then(({
              data
            }) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.inventorybomService.list({
                'current': 1,
                'size': 999,
                'inventory.id': this.inputForm.inventory.id
              }).then(({
                data
              }) => {
                this.exitsBomList = data.records
              })
              this.loading = false
            })
          }
        })
      },
      saveInventorybomDetailRow(child) {
        if (child[0] === '') {
          this.inputForm.inventorybomDetailDTOList.push(child[1])
        } else {
          this.inputForm.inventorybomDetailDTOList.forEach((item, index) => {
            if (item === child[0]) {
              this.inputForm.inventorybomDetailDTOList.splice(index, 1, child[1])
            }
          })
        }
      },
      addInventorybomDetailRow(child) {
        this.$refs.inventorybomDetailForm.init('add')
      },
      viewInventorybomDetailRow(child) {
        this.$refs.inventorybomDetailForm.init('view', child)
      },
      editInventorybomDetailRow(child) {
        this.$refs.inventorybomDetailForm.init('edit', child)
      },
      delInventorybomDetailRow(child) {
        this.inputForm.inventorybomDetailDTOList.forEach((item, index) => {
          if (item === child && item.id === '') {
            this.inputForm.inventorybomDetailDTOList.splice(index, 1)
          } else if (item === child) {
            item.delFlag = '1'
            this.inputForm.inventorybomDetailDTOList.splice(index, 1, item)
          }
        })
      },
      // 表单提交
      doSubmit() {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.inventorybomService.save(this.inputForm).then(({
              data
            }) => {
              this.visible = false
              this.$message.success(data)
              this.$emit('refreshDataList')
              this.loading = false
            }).catch(() => {
              this.loading = false
            })
          }
        })
      },
      getExitsBoms(da) {
        this.loading = true
        this.inventorybomService.list({
          'current': 1,
          'size': 999/* ,
          'inventory.id': da[0].id */
        }).then(({
          data
        }) => {
          this.exitsBomList = data.records
          this.loading = false
        })
      },
      addBOMList() {
        /* 选择已有的BOM 将该BOM 添加到新的BOM里面 */
        if (this.inputForm.exitsBom) {
          this.loading = true
          this.inventorybomService.queryById(this.inputForm.exitsBom).then(({
            data
          }) => {
            var inventorybomDetailDTOList = data.inventorybomDetailDTOList
            for (var i = 0; i < inventorybomDetailDTOList.length; i++) {
              inventorybomDetailDTOList[i].inventorybom.id = ''
              inventorybomDetailDTOList[i].id = ''
              inventorybomDetailDTOList[i].createBy = ''
              inventorybomDetailDTOList[i].createDate = ''
              inventorybomDetailDTOList[i].updateBy = ''
              inventorybomDetailDTOList[i].updateDate = ''
            }
            this.inputForm.inventorybomDetailDTOList = data.inventorybomDetailDTOList
            this.loading = false
          })
        } else {
          this.inputForm.inventorybomDetailDTOList = []
        }
      }
    }
  }
</script>
<style >
.eloption .el-select-dropdown__wrap {
      max-height: 350px !important;
    }
</style>
