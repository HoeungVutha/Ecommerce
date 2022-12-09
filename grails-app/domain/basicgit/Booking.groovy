package basicgit

import grails.converters.JSON

class Booking {
    String staffName
    String code
    String roomNumber
    String price
    String address

    Date lastUpdated
    Date dateCreated
    static constraints = {
    }
    static {
        JSON.registerObjectMarshaller(Booking,{ Booking booking->
            Map map=new LinkedHashMap<>(booking.properties)
            map.id=booking.id
            return map
        })
    }
}
