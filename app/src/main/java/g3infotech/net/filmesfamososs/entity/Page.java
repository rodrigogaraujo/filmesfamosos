package g3infotech.net.filmesfamososs.entity;

import java.util.List;

/**
 * Created by g3infotech on 07/12/17.
 */

public class Page<T>{

    private List<T> results;


    @Override
    public String toString() {
        return "Page{" +
                ", results=" + results +
                '}';
    }


    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
