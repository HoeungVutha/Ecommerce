import corebackend.odp.CoordinateValidatorService
import corebackend.security.CustomLogoutHandlerService
import corebackend.security.CustomTokenStorageService
import corebackend.security.SecUserPasswordEncoderListener
import ecoinsoft.corebackend.simplegenericrestfulcontroller.service.LogInterceptService


// Place your Spring DSL code here
beans = {
    tokenStorageService(CustomTokenStorageService)

    coordinateValidator(CoordinateValidatorService)

    secUserPasswordEncoderListener(SecUserPasswordEncoderListener)

    customLogoutHandler(CustomLogoutHandlerService){

    }

    iLogInterceptService(LogInterceptService) { bean ->
        bean.autowire = true
    }

}
