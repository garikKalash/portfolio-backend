package com.gk.portfolio.services

import com.gk.portfolio.AppIntegrationTest
import com.gk.portfolio.models.UserModel
import org.springframework.beans.factory.annotation.Autowired

class UserServiceTest extends AppIntegrationTest{
    @Autowired
    UserService userService


    def "get user"() {

        when:
        def user1 = userService.getById(1)

        then:
        user1.name == 'Garik'
        user1.surname == 'Kalashyan'
        user1.email == 'garik.kalash@gmail.com'

        when:
        def user2 = userService.getById(2)
        then:
        user2.name == 'hr'
        user2.surname == 'hr'
        user2.email == 'hr@hr.com'

        when:
        def user3 = userService.getById(3)
        then:
        user3.name == 'tech_guy'
        user3.surname == 'tech_guy'
        user3.email == 'tech@tech.com'


    }

    def "update user"() {
        given:
        UserModel model = new UserModel()
        model.name = 'newHr'
        model.surname = 'newHr'
        model.address ='hr'
        model.email = 'hr@hr.com'
        model.password = '$2a$10$sq2xIrFgcRsaRZSlrjciUuwTsRKUJOGOSfJT03iTg3vtiI5AvmirS'
        model.role = 'hr'
        model.phone = 'hr'
        model.skype = 'hr'

        when:
        def user = userService.getById(2)
        then:
        user.name == 'hr'
        user.surname == 'hr'

        when:
        def updated = userService.update(2, model)

        then:
        updated.getId() == 2
        updated.getName() == 'newHr'
        updated.getSurname() == 'newHr'

        cleanup:
        model.name = 'hr'
        model.surname = 'hr'
        userService.update(2, model)



    }


}
