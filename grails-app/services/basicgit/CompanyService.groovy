package basicgit

import ecoinsoft.corebackend.simplegenericrestfulcontroller.generic.PaginationCommand
import grails.gorm.transactions.Transactional

@Transactional
class CompanyService {

    def outputService(String staffName, PaginationCommand pagination) {
        def company = Company.createCriteria().list(pagination.params, {
            if (staffName) {
                like("staffName", "%$staffName%")
            }
        })
        return company
    }
}
