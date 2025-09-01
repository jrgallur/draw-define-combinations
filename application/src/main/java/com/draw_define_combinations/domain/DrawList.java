package com.draw_define_combinations.domain;

import com.draw_define_combinations.domain.types.TDateInteger;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawList {
    private Map<TDateInteger, Draw> draws = new HashMap<>();
    private Map<TDateInteger, Integer> orderedNumber = new HashMap<>();
    private Map<TDateInteger, TDateInteger> previousDate = new HashMap<>();
    private Map<TDateInteger, TDateInteger> nextDate = new HashMap<>();

    /**
     * -- GETTER --
     *  Devuelve la fecha del primer sorteo registrado
     */
    @Getter
    private TDateInteger firstDrawDate;

    /**
     * -- GETTER --
     *  Devuelve la fecha del último sorteo registrado
     */
    @Getter
    private TDateInteger lastDrawDate;

    /**
     * Devuelve el índice del sorteo en la lista ordenada
     *
     * @param dateInteger Fecha del sorteo del cual se quiere obtener el número de orden
     * @return 1 para el primer sorteo y el número correspondiente para el resto
     */
    public Integer getOrderedNumber(TDateInteger dateInteger) {
        return orderedNumber.get(dateInteger);
    }

    /**
     * Devuelve la fecha del anterior sorteo a la recibida
     *
     * @param dateInteger Fecha del sorteo
     * @return La fecha del anterior sorteo a la recibida o null si no existe
     */
    public TDateInteger getPreviousDate(TDateInteger dateInteger) {
        return previousDate.get(dateInteger);
    }

    /**
     * Devuelve el sorteo anterior a uno dado.
     *
     * @param dateInteger Fecha en la que se busca. Se corresponde con un sorteo (existe un sorteo de la lista con esa fecha)
     * @return El sorteo anterior a la fecha recibida como parámetro o null si no hay otro
     */
    public Draw getPreviousDraw(TDateInteger dateInteger) {
        return draws.get(getPreviousDate(dateInteger));
    }

    /**
     * Busca la fecha del sorteo anterior a una fecha dada
     *
     * @param date Fecha a partir de la cual se hace la búsqueda
     * @return La fecha del sorteo anterior a la fecha recibida como parámetro o null si no se encuentra ninguna
     */
    public TDateInteger findExistingPreviousDrawDate(TDateInteger date) {
        // Primero busco la fecha anterior en el hash
        TDateInteger previousDrawDate = previousDate.get(date);
        // Si no la tengo, hay que hacer una búsqueda a mano
        if (previousDrawDate == null && firstDrawDate.compareTo(date) < 0) {
            // Compruebo si existe alguna fecha en la lista menor a la recibida
            TDateInteger ds = new TDateInteger(date.toInteger());
            while (previousDrawDate == null) {
                ds.add(TDateInteger.addTypes.DAY, -1);
                previousDrawDate = previousDate.get(ds);
            }
        }

        return previousDrawDate;
    }

    /**
     * Busca el sorteo anterior a una fecha dada
     *
     * @param dateInteger Fecha a partir de la cual se hace la búsqueda
     * @return El sorteo anterior a la fecha recibida como parámetro
     */
    public Draw findPreviousDraw(TDateInteger dateInteger) {
        return draws.get(findExistingPreviousDrawDate(dateInteger));
    }

    /**
     * Devuelve la fecha del siguiente sorteo a la recibida
     *
     * @param dateInteger Fecha del sorteo
     * @return La fecha del siguiente sorteo a la recibida o null si no existe
     */
    public TDateInteger getNextDate(TDateInteger dateInteger) {
        return nextDate.get(dateInteger);
    }

    /**
     * Devuelve el sorteo siguiente a uno dado.
     *
     * @param dateInteger Fecha en la que se busca. Se corresponde con un sorteo (existe un sorteo de la lista con esa fecha)
     * @return El sorteo siguiente a la fecha recibida como parámetro o null si no hay otro
     */
    public Draw getNextDraw(TDateInteger dateInteger) {
        return draws.get(getNextDate(dateInteger));
    }

    /**
     * Busca la fecha del sorteo siguiente a una fecha dada
     *
     * @param date Fecha a partir de la cual se hace la búsqueda
     * @return La fecha del sorteo siguiente a la fecha recibida como parámetro o null si no se encuentra ninguna
     */
    public TDateInteger findExistingNextDate(TDateInteger date) {
        // Primero busco la fecha anterior en el hash
        TDateInteger nextDrawDate = previousDate.get(date);
        // Si no la tengo, hay que hacer una búsqueda a mano
        if (nextDrawDate == null && lastDrawDate.compareTo(date) > 0) {
            // Compruebo si existe alguna fecha en la lista mayor a la recibida
            TDateInteger ds = new TDateInteger(date.toInteger());
            while (nextDrawDate == null) {
                ds.add(TDateInteger.addTypes.DAY, -1);
                nextDrawDate = nextDate.get(ds);
            }
        }

        return nextDrawDate;
    }

    /**
     * Busca el sorteo siguiente a una fecha dada
     *
     * @param dateInteger Fecha a partir de la cual se hace la búsqueda
     * @return El sorteo siguiente a la fecha recibida como parámetro
     */
    public Draw findNextDraw(TDateInteger dateInteger) {
        return draws.get(findExistingNextDate(dateInteger));
    }

    /**
     * Devuelve un sorteo a partir de una fecha
     *
     * @param dateInteger Fecha del sorteo
     * @return El sorteo correspondiente a esa fecha o null si no se encuentra ninguno
     */
    public Draw getDraw(TDateInteger dateInteger) {
        return draws.get(dateInteger);
    }

    /**
     * Guarda una lista de sorteos de manera ordenada
     *
     * @param drawList Lista de sorteos a guardar
     */
    public void setDrawList(List<Draw> drawList) {
        // Ordenamos la lista de sorteos
        drawList.sort(null);
        draws = new HashMap<>();
        orderedNumber = new HashMap<>();
        previousDate = new HashMap<>();
        nextDate = new HashMap<>();
        // Recorremos la lista (ahora ordenada) secuencialmente
        for (int cont = 0; cont < drawList.size(); cont++) {
            draws.put(drawList.get(cont).getDate(), drawList.get(cont));
            orderedNumber.put(drawList.get(cont).getDate(), cont + 1);
            if (cont > 0) {
                previousDate.put(drawList.get(cont).getDate(), drawList.get(cont - 1).getDate());
                nextDate.put(drawList.get(cont - 1).getDate(), drawList.get(cont).getDate());
            }
        }
        // Establecemos la primera y la última fecha de los sorteos
        firstDrawDate = drawList.get(0).getDate();
        lastDrawDate = drawList.get(drawList.size() - 1).getDate();
    }

    /**
     * Comprueba si existe un sorteo en una fecha determinada
     *
     * @param dateInteger Fecha en la que se verifica si existe o no el sorteo
     * @return True si existe un sorteo en esa fecha
     */
    public boolean existsDrawInDate(TDateInteger dateInteger) {
        return draws.containsKey(dateInteger);
    }

    /**
     * Añade un sorteo. Espera que se añadan en orden
     *
     * @param draw Sorteo a añadir que es el "siguiente" al anterior
     */
    public void addNextDraw(Draw draw) {
        // Añadimos el índice fecha -> sorteo
        if (!draws.isEmpty()) {
            // Añadimos los índices de "fecha anterior" y "fecha posterior". Nos basamos en la fecha del último sorte.
            previousDate.put(draw.getDate(), lastDrawDate);
            nextDate.put(lastDrawDate, draw.getDate());
        } else {
            // Establecemos la primera fecha de los sorteos
            firstDrawDate = draw.getDate();
        }
        // Establecemos la última fecha de los sorteos
        lastDrawDate = draw.getDate();
        // Añadimos el sorteo a la lista
        draws.put(draw.getDate(), draw);
        orderedNumber.put(draw.getDate(), draws.size());
    }
}