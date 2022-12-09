package basicgit

import ecoinsoft.corebackend.simplegenericrestfulcontroller.generic.PaginationCommand
import grails.gorm.transactions.Transactional

@Transactional
class ProductService {

    def outputService(String productName, PaginationCommand pagination) {
        def product = Product.createCriteria().list(pagination.params, {
            if (productName) {
                like("productName", "%$productName%")
            }
        })
        return product
    }
}
