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
    def getCompanyDetail(Long id){
        def company = Company.get(id)
        def result = mapCompany(company)
        return result

    }

    def mapCompany(def company) {
        List<Map> companyList = new ArrayList<>()
        company.collect { it ->

            def booking = Booking.findById(it.bookingId)

            def map = [:]

            //  Property Company
             map.id = it?.id
             map.staffName = it?.staffName
             map.code = it?.code
             map.gender = it?.gender
             map.address = it?.address
             map.salary = it?.salary
             map.bookingId = it?.bookingId


            map.booking = mapBooking(booking)
            companyList.add(map)
        }
        return companyList
    }

    def mapBooking(def booking) {
        List<Map> book = new ArrayList<>()
        booking.collect { it ->
            def map = [:]
            // Property Booking
            map.id = it?.id
            map.staffName = it?.staffName
            map.code = it?.code
            map.roomNumber = it?.roomNumber
            map.price = it?.price
            map.address = it?.address
            book.add(map)
        }
        return book
    }
}
