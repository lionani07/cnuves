package cf;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Calendar;

public class Test {

	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2019, 8, 02);
		int year = calendar.get(Calendar.YEAR);
		int diaMes = calendar.get(Calendar.DAY_OF_MONTH);
		int mes = calendar.get(Calendar.MONTH);
		LocalTime inicio = LocalTime.of(06, 00);

		LocalDateTime dateTimetoSave = LocalDateTime.of(year, mes, diaMes, inicio.getHour(), inicio.getMinute());
		
		System.out.println(existInterval(dateTimetoSave));
	}

	public static boolean existInterval(LocalDateTime date) {
		System.out.println("date to save" + date);
		LocalDateTime localInicio = LocalDateTime.of(2019, Month.AUGUST, 02, 06, 30);
		LocalDateTime localFim = LocalDateTime.of(2019, Month.AUGUST, 02, 07, 30);	
		System.out.println("date inicio DB"+ localInicio);
		System.out.println("date fim DB" + localFim);
		if (date.isAfter(localInicio) && date.isBefore(localFim)) {
			return true;
		}
		return false;
	}

}
