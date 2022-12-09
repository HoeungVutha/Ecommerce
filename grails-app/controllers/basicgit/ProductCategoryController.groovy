package basicgit

import ecoinsoft.corebackend.simplegenericrestfulcontroller.generic.JSONFormat
import ecoinsoft.corebackend.simplegenericrestfulcontroller.generic.PaginationCommand
import ecoinsoft.corebackend.simplegenericrestfulcontroller.generic.SimpleGenericRestfulController
import grails.converters.JSON
import grails.gorm.transactions.Transactional

class ProductCategoryController extends SimpleGenericRestfulController<ProductCategory>{

    ProductCategoryController() {
        super(ProductCategory)
    }
    def productCategoryService
    @Transactional
    @Override
    def index(PaginationCommand pagination) {
        String productName = params.productName
        def productCategory = productCategoryService.outputService(productName, pagination)
        render JSONFormat.respond(productCategory) as JSON
    }
    @Override
    def show(){
        Long id = params.long("id")
        def productCategory = productCategoryService.getProductCategoryDetail(id)

        render JSONFormat.respond(productCategory) as JSON
    }
}
