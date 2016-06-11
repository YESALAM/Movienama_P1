
package me.seatech.movienama.schemas;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TrailerResult {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("results")
    @Expose
    private List<Trailer> trailers = new ArrayList<Trailer>();

    /**
     * 
     * @return
     *     The id
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The results
     */
    public List<Trailer> getTrailers() {
        return trailers;
    }

    /**
     * 
     * @param trailers
     *     The results
     */
    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

}
