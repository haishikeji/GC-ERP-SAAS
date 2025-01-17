<template>
  <div>
    <el-dialog :title="title" :close-on-click-modal="false" v-dialogDrag :visible.sync="visible" width="80%">
      <el-form :model="inputForm" size="small" ref="inputForm" v-loading="loading"
        :class="method==='view'?'readonly':''" :disabled="method==='view'" label-width="120px">
        <el-row :gutter="15">
          <el-col :span="12">
            <!-- <el-form-item label="表名" prop="tableName"
                :rules="[
                 ]">
              <el-input v-model="inputForm.tableName" placeholder="请填写表名"></el-input>
           </el-form-item> -->
            <el-form-item label="表名" prop="tableName">
              <el-select v-model="inputForm.tableName" placeholder="请选择" style="width: 100%;">
                <el-option v-for="item in $dictUtils.getDictList('biao_type')" :key="item.value" :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item v-if="inputForm.tableName.includes('发货清单')" label="显示顺序">
              <span v-for="(item,index) in mainFieldList" :key="index"> {{(index+1) +':'+item.split(':')[0]+";"}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item v-if="inputForm.tableName.includes('发货清单')" label="主表字段" prop="mainField" :rules="[]">
              <el-checkbox-group v-model="mainFieldList">
                <el-checkbox v-for="mainField in $dictUtils.getDictList('main_pring_type')" :label="mainField.value"
                  :key="mainField.value">{{mainField.label}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item v-if="inputForm.tableName.includes('发货清单')" label="显示顺序">
              <span v-for="(item,index) in scheduleFieldList" :key="index">
                {{(index+1) +':'+item.split(':')[0]+";"}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item v-if="inputForm.tableName.includes('发货清单')" label="附表字段" prop="scheduleField" :rules="[
                 ]">
              <el-checkbox-group v-model="scheduleFieldList">
                <el-checkbox v-for="scheduleField in $dictUtils.getDictList('fubiao_type')" :label="scheduleField.value"
                  :key="scheduleField.value">{{scheduleField.label}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item v-if="inputForm.tableName.includes('采购订单')" label="显示顺序">
              <span v-for="(item,index) in mainFieldList" :key="index"> {{(index+1) +':'+item.split(':')[0]+";"}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item v-if="inputForm.tableName.includes('采购订单')" label="主表字段" prop="mainField" :rules="[]">
              <el-checkbox-group v-model="mainFieldList">
                <el-checkbox v-for="mainField in $dictUtils.getDictList('purchase_print_setup_1')" :label="mainField.value"
                  :key="mainField.value">{{mainField.label}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item v-if="inputForm.tableName.includes('采购订单')" label="显示顺序">
              <span v-for="(item,index) in scheduleFieldList" :key="index">
                {{(index+1) +':'+item.split(':')[0]+";"}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item v-if="inputForm.tableName.includes('采购订单')" label="附表字段" prop="scheduleField" :rules="[
                 ]">
              <el-checkbox-group v-model="scheduleFieldList">
                <el-checkbox v-for="scheduleField in $dictUtils.getDictList('purchase_print_setup_2')" :label="scheduleField.value"
                  :key="scheduleField.value">{{scheduleField.label}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item v-if="inputForm.tableName.includes('销售订单')" label="显示顺序">
              <span v-for="(item,index) in mainFieldList" :key="index"> {{(index+1) +':'+item.split(':')[0]+";"}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item v-if="inputForm.tableName.includes('销售订单')" label="主表字段" prop="mainField" :rules="[]">
              <el-checkbox-group v-model="mainFieldList">
                <el-checkbox v-for="mainField in $dictUtils.getDictList('saleorder_print_setup_1')" :label="mainField.value"
                  :key="mainField.value">{{mainField.label}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item v-if="inputForm.tableName.includes('销售订单')" label="显示顺序">
              <span v-for="(item,index) in scheduleFieldList" :key="index">
                {{(index+1) +':'+item.split(':')[0]+";"}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item v-if="inputForm.tableName.includes('销售订单')" label="主表字段" prop="mainField" :rules="[]">
              <el-checkbox-group v-model="mainFieldList">
                <el-checkbox v-for="mainField in $dictUtils.getDictList('saleorder_print_setup_1')" :label="mainField.value"
                  :key="mainField.value">{{mainField.label}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item v-if="inputForm.tableName.includes('生产计划单')" label="主表字段" prop="mainField" :rules="[]">
              <el-checkbox-group v-model="mainFieldList">
                <el-checkbox v-for="mainField in $dictUtils.getDictList('produce_print_setup_1')" :label="mainField.value"
                  :key="mainField.value">{{mainField.label}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item v-if="inputForm.tableName.includes('生产计划单')" label="显示顺序">
              <span v-for="(item,index) in scheduleFieldList" :key="index">
                {{(index+1) +':'+item.split(':')[0]+";"}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item v-if="inputForm.tableName.includes('生产计划单')" label="附表字段" prop="scheduleField" :rules="[
                 ]">
              <el-checkbox-group v-model="scheduleFieldList">
                <el-checkbox v-for="scheduleField in $dictUtils.getDictList('produce_print_setup_2')" :label="scheduleField.value"
                  :key="scheduleField.value">{{scheduleField.label}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="visible = false">关闭</el-button>
        <el-button size="small" type="primary" v-if="method != 'view'" @click="doSubmit()" v-noMoreClick>确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import PrintsService from '@/api/wh/prints/PrintsService'
  export default {
    data() {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        inputForm: {
          id: '',
          tableName: '',
          mainField: '',
          scheduleField: ''
        }
      }
    },
    components: {},
    printsService: null,
    created() {
      this.printsService = new PrintsService()
    },
    computed: {
      mainFieldList: {
        get: function() {
          return this.inputForm.mainField !== '' ? this.inputForm.mainField.split(',') : []
        },
        set: function(val) {
          this.inputForm.mainField = val.join(',')
        }
      },
      scheduleFieldList: {
        get: function() {
          return this.inputForm.scheduleField !== '' ? this.inputForm.scheduleField.split(',') : []
        },
        set: function(val) {
          this.inputForm.scheduleField = val.join(',')
        }
      }
    },
    methods: {
      init(method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建打印配置`
        } else if (method === 'edit') {
          this.title = '修改打印配置'
        } else if (method === 'view') {
          this.title = '查看打印配置'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.printsService.queryById(this.inputForm.id).then(({
              data
            }) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.loading = false
            })
          }
        })
      },
      // 表单提交
      doSubmit() {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.printsService.save(this.inputForm).then(({
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
      }
    }
  }
</script>
