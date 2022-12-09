package basicgit

import ecoinsoft.corebackend.simplegenericrestfulcontroller.generic.JSONFormat
import ecoinsoft.corebackend.simplegenericrestfulcontroller.generic.PaginationCommand
import ecoinsoft.corebackend.simplegenericrestfulcontroller.generic.SimpleGenericRestfulController
import grails.converters.JSON

class BookingController extends SimpleGenericRestfulController<Booking> {

   BookingController() {
       super(Booking)
   }
    def bookingService
    @Override
    def index(PaginationCommand pagination) {
        String staffName = params.staffName
        def booking = bookingService.outputService(staffName, pagination)
        render JSONFormat.respond(booking) as JSON
    }
}
