package basicgit

import ecoinsoft.corebackend.simplegenericrestfulcontroller.generic.JSONFormat
import ecoinsoft.corebackend.simplegenericrestfulcontroller.generic.PaginationCommand
import ecoinsoft.corebackend.simplegenericrestfulcontroller.generic.SimpleGenericRestfulController
import ecoinsoft.corebackend.simplegenericrestfulcontroller.generic.StatusCode
import grails.converters.JSON
import grails.gorm.transactions.Transactional


class ProductController extends SimpleGenericRestfulController<Product>{

        ProductController() {
        super(Product)
    }
    def productService
    @Override
    def index(PaginationCommand pagination) {
        String productName = params.productName
        def product = productService.outputService(productName, pagination)
        render JSONFormat.respond(product) as JSON
    }
    @Transactional
    @Override
    def saveAction(){
        def requestJson=request.JSON
        def product=new Product()
        bindData(product,requestJson)
        product.validate()
        if(product.hasErrors()){
            transactionStatus.setRollbackOnly()
            render JSONFormat.respond(null, StatusCode.Invalid, getError(product)) as JSON
            return
        }
        product.save()
        render JSONFormat.respond(product) as JSON
    }
    @Override
    def update(){
        def requestJson=request.JSON
        def product=Product.get(params.long("id"))
        bindData(product,requestJson)
        if(!product){
            notFound()
            return
        }
        product.save()
        render JSONFormat.respond(product) as JSON
    }
    @Override
    def delete(){
        def product=Product.get(params.long("id"))
        if(!product){
            notFound()
            return
        }
        product.delete()
        render JSONFormat.respond(product) as JSON
    }
}

