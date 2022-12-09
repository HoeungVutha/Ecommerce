package basicgit

import ecoinsoft.corebackend.simplegenericrestfulcontroller.generic.PaginationCommand
import grails.gorm.transactions.Transactional

@Transactional
class BookingService {

    def outputService(String staffName, PaginationCommand pagination) {
        def booking = Booking.createCriteria().list(pagination.params, {
            if (staffName) {
                like("staffName", "%$staffName%")
            }
        })
        return booking
    }
}