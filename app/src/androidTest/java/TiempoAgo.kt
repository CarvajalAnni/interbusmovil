import java.util.concurrent.TimeUnit

private const val SECOND_MILLIS=1
private const val MINUTE_MILLIS=60 * SECOND_MILLIS
private const val HOUR_MILLIS=60 * MINUTE_MILLIS
private const val DAY_MILLIS=24 * HOUR_MILLIS

object TiempoAgo {

    fun getTiempo(time: Int):String{

        val ahora= TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
        if (time>ahora || time<=0){
            return "Futuro"
        }

        val  diferencia= ahora-time
        return when{
            diferencia< MINUTE_MILLIS -> "Justo ahora"
            diferencia< 2*MINUTE_MILLIS -> "Hace un minuto"
            diferencia< 60*MINUTE_MILLIS -> "${diferencia/MINUTE_MILLIS} Minutos"
            diferencia< 2*HOUR_MILLIS -> "Hace una hora"
            diferencia< 24*HOUR_MILLIS -> "${diferencia/HOUR_MILLIS} Horas"
            diferencia< 48*HOUR_MILLIS -> "Ayer"
            else-> "${diferencia/DAY_MILLIS} Dias"
        }
    }
}