fun main(args: Array<String>) {
    // Создаю парковку, заполняю ее на 20 мест
    val parkingSize = 20 //размер парковки
    var parking = mutableMapOf<String, String?>()
    for (i in 1..parkingSize) {
        parking["P$i"] = null
    }
    var parked = 0//  объявляю переменную - число припаркованных машин
    var manager = Manager()//создаю менеджера парковки
    val returnRegex =
        """^\/return [a-zA-z]\d{3}[a-zA-z]{2}\d{2} [a-zA-Z_]{1,}$""".toRegex()//создаю маску ввода для команды /return
    val parkRegex =
        """^\/park [a-zA-z_]{3,} [a-zA-z_]{1,} [a-zA-z]\d{3}[a-zA-z]{2}\d{2} [a-zA-Z_]{1,}${'$'}""".toRegex()//создаю маску ввода для команды /park
    val parkInfoByCarRegex =
        """^/park_info_by_car [a-zA-z]\d{3}[a-zA-z]{2}\d{2}""".toRegex()//создаю маску ввода для команды /park_info_by_car
    val parkInfoByPlaceRegex =
        """^/park_info_by_place P([1-9]{1}$|1[0-9]|20$)""".toRegex()//создаю маску ввода для команды /park_info_by_place
    //основная часть программы
    while (true) {
        print("Введите команду: ")
        var userCommand: String = readln() //ввод пользователя
        //проверяю, что ввел пользователь - разбиваю строку на слова в списке
        when (userCommand.split(" ")[0]) {
            "/start" -> println("Добро пожаловать на парковку!") //если start - вывожу приветствие
            // если /help - вывожу подсказку какие есть команды - что они означают
            "/help" -> println(
                "/start - Отобразаить приветствие\n" +
                        "/end - Завершить работу\n" +
                        "/park_stats - Показать заполненность парковки\n" +
                        "/park цвет марка_машины номер_машины имя_владельца - припарковать машину\n" +
                        "/return марка номер - забрать машину с парковки\n" +
                        "/park_info_by_car номер -- возвращает место, где припаркована машина, по ее номеру\n" +
                        "/park_info_by_place -- возвращает информацию о машине по месту на парковке\n" +
                        "/park_all_stats - возвращает сколько всего машин было припарковано за все время работы программы"
            )

            "/park" -> {

                //проверяется соответствие того что ввел пользоваетль - формату ввода для этой команды с использованием рег выр
                if (parkRegex.matches(userCommand)) {

                    var car = Car(
                        userCommand.split(" ")[1],
                        userCommand.split(" ")[2],
                        userCommand.split(" ")[3],
                        userCommand.split(" ")[4]
                    )
                    manager.parkTheCar(car, parking)
                    parked += 1 // счетчик припаркованных машин, используется в команде /park_all_stats

                } else println("Неверный формат! Правильно: '/park цвет марка_машины номер_машины имя_владельца (/park white honda_accord a001aa27 anton_frolov)'")

            }
            // анонимная функция пробегает по всему массиву parking и выводит ключ - значение
            "/park_stats" -> {
                parking.forEach { t, u -> println("$t : ${u ?: "Свободно"}\n") }
            }
            // проверяю соответствие ввода пользователя рег выражению returnRegex, соответствует - создаю владельца owner, вызываю функцию pickup
            //передаю ей созданные параметры и парковку, не соответствует - вывод сообщения
            "/return" -> {
                if (returnRegex.matches(input = userCommand)) {

                    var owner = Owner(carNumber = userCommand.split(" ")[1], ownerName = userCommand.split(" ")[2])
                    manager.pickUp(owner.carNumber, parking, owner.ownerName)

                } else {
                    println("Данные введены некорректно! Правильно: '/return номер имя_владельца (/return a001aa27 anton_frolov)'")
                }
            }
            //выход из программы
            "/exit" -> {
                println("До свидания!")
                break
            }
            //вызываю функцию manager.parkInfoByCar если ввод соответствует заданному рег выр, иначе - сообщение
            "/park_info_by_car" -> {
                if (parkInfoByCarRegex.matches(userCommand)) {
                    val car_number = userCommand.split(" ")[1]
                    manager.parkInfoByCar(car_number, parking)
                } else println("Неверный формат! Правильно: '/park_info_by_car номер (/park_info_by_car a001aa27)'")

            }
            //вызываю функцию manager.parkInfoByPlace если ввод соответствует заданному рег выр, иначе - сообщение
            "/park_info_by_place" -> {
                if (parkInfoByPlaceRegex.matches(userCommand)) {

                    manager.parkInfoByPlace(parking, userCommand.split(" ")[1])
                } else println("Неверный формат! Правильно: '/park_info_by_place номер_парковочного_места (/park_info_by_place P1 - P20)'")
            }

            "/park_all_stats" -> println("Всего было припарковано $parked")
            else -> println("Программа не может оработать запрос. Введите '/help'")
        }
    }

}




