package basicgit

import ecoinsoft.corebackend.simplegenericrestfulcontroller.generic.JSONFormat
import ecoinsoft.corebackend.simplegenericrestfulcontroller.generic.PaginationCommand
import ecoinsoft.corebackend.simplegenericrestfulcontroller.generic.SimpleGenericRestfulController
import ecoinsoft.corebackend.simplegenericrestfulcontroller.generic.StatusCode
import grails.converters.JSON
import grails.gorm.transactions.Transactional

class CompanyController extends SimpleGenericRestfulController<Company>{

    CompanyController() {
        super(Company)
    }
    def companyService
    @Override
    def index(PaginationCommand pagination) {
        String staffName = params.staffName
        def company = companyService.outputService(staffName, pagination)
        render JSONFormat.respond(company) as JSON
    }
    @Transactional
    @Override
    def saveAction(){
        def requestJson=request.JSON
        def company=new Company()
        bindData(company,requestJson)
        company.validate()
        if(company.hasErrors()){
            transactionStatus.setRollbackOnly()
            render JSONFormat.respond(null, StatusCode.Invalid, getError(company)) as JSON
            return
        }
        company.save()
        render JSONFormat.respond(company) as JSON
    }
    @Override
    def update(){
        def requestJson=request.JSON
        def company=Company.get(params.long("id"))
        bindData(company,requestJson)
        if(!company){
            notFound()
            return
        }
        company.save()
        render JSONFormat.respond(company) as JSON
    }
    @Override
    def delete(){
        def company=Company.get(params.long("id"))
        if(!company){
            notFound()
            return
        }
        company.delete()
        render JSONFormat.respond(company) as JSON
    }

}
