package basicgit

import ecoinsoft.corebackend.simplegenericrestfulcontroller.generic.PaginationCommand
import grails.gorm.transactions.Transactional

@Transactional
class ProductCategoryService {

    def outputService(String productName, PaginationCommand pagination) {
        def productCategory = ProductCategory.createCriteria().list(pagination.params, {
            if (productName) {
                like("productName", "%$productName%")
            }
        })
        return productCategory
    }

    def getProductCategoryDetail(Long id){
        def productCategory = ProductCategory.get(id)
        def result = mapProductCategory(productCategory)
        return result

    }

    def mapProductCategory(def productCategory) {
        List<Map> productCategoryList = new ArrayList<>()
        productCategory.collect { it ->

            def product = Product.findAllByProductCategory(it)
            def company = Company.findAllByProductCategory(it)
            def map = [:]
            // Property ProductCategory
            map.id = it?.id
            map.productName = it?.productName
            map.code = it?.code
            map.productCountry = it?.productCountry
            map.price = it?.price

            map.product = mapProduct(product)
            map.company = mapCompany(company)
            productCategoryList.add(map)
        }
        return productCategoryList
    }

    def mapProduct(def product) {
        List<Map> book = new ArrayList<>()
        product.collect { it ->
            def map = [:]
            // Property Product
            map.id = it?.id
            map.productName = it?.productName
            map.code = it?.code
            map.productYear = it?.productYear
            map.price = it?.price
            book.add(map)
        }
        return book
    }

    def mapCompany(def company) {
        List<Map> booking = new ArrayList<>()
        company.collect { it ->
            def map = [:]
            // Property Company
            map.id = it?.id
            map.staffName = it?.staffName
            map.code = it?.code
            map.gender = it?.gender
            map.address = it?.address
            map.salary = it?.salary
            booking.add(map)
        }
        return booking
    }
}
