package com.microsoft.recognizers.text.datetime.utilities;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

public class DurationParsingUtil {
  public static boolean isTimeDurationUnit(String unitStr) {
    boolean result = false;
    switch (unitStr) {
      case "H":
      result = true;
      break;
      case "M":
      result = true;
      break;
      case "S":
      result = true;
      break;
    }
    return result;
  }
  
  public static boolean isMultipleDuration(String timex) {
    ImmutableMap<String, Double> map = resolveDurationTimex(timex);
    return map.size() > 1;
  }
  
  public static boolean isDateDuration(String timex) {
    ImmutableMap<String, Double> map = resolveDurationTimex(timex);
    
    for (String unit : map.keySet()) {
      if (isTimeDurationUnit(unit)) {
        return false;
      }
    }
    
    return true;
  }
  
  public static LocalDateTime shiftDateTime(String timex, LocalDateTime reference, boolean future) {
    ImmutableMap<String, Double> timexUnitMap = resolveDurationTimex(timex);
    
    return getShiftResult(timexUnitMap, reference, future);
  }
  
  public static LocalDateTime getShiftResult(ImmutableMap<String, Double> timexUnitMap, LocalDateTime reference, boolean future) {
    LocalDateTime result = reference;
    int futureOrPast = future ? 1 : -1;
    for (Map.Entry<String, Double> pair : timexUnitMap.entrySet()) {
      String unit = pair.getKey();
      Double number = pair.getValue();
      switch (unit) {
        case "H":
        result = result.plusHours(Math.round(number * futureOrPast));
        break;
        case "M":
        result = result.plusMinutes(Math.round(number * futureOrPast));
        break;
        case "S":
        result = result.plusSeconds(Math.round(number * futureOrPast));
        break;
        case "D":
        result = result.plusDays(Math.round(number * futureOrPast));
        break;
        case "W":
        result = result.plusWeeks(Math.round(number * futureOrPast));
        break;
        case "MON":
        result = result.plusMonths(Math.round(number * futureOrPast));
        break;
        case "Y":
        result = result.plusYears(Math.round(number * futureOrPast));
        break;
        
        default:
        return result;
      }
    }
    return result;
  }
  
  private static ImmutableMap<String, Double> resolveDurationTimex(String timex) {
    Builder<String, Double> resultBuilder = ImmutableMap.builder();
    
    // resolve duration timex, such as P21DT2H(21 days 2 hours)
    String durationStr = timex.replace('P', '\0');
    int numberStart = 0;
    boolean isTime = false;
    
    for (int i = 0; i < durationStr.length(); i++) {
      if (Character.isLetter(durationStr.charAt(i))) {
        if (durationStr.charAt(i) == 'T') {
          isTime = true;
        } else {
          String numStr = durationStr.substring(numberStart, i);
          
          try {
            Double number = Double.parseDouble(numStr);
            String srcTimexUnit = durationStr.substring(i, i + 1);
            
            if (!isTime && srcTimexUnit == "M") {
              srcTimexUnit = "MON";
            }
            
            resultBuilder.put(srcTimexUnit, number);
            
          } catch (NumberFormatException e) {
            return resultBuilder.build();
          }
          
        }
        numberStart = i + 1;
      }
    }
    
    return resultBuilder.build();
  }
}