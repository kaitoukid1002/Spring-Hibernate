package vn.viettel.browser.webservice.entity;

import javax.persistence.*;

/**
 * Created by quytx on 31/10/17.
 * vn.viettel.browser.webservice.dao : NewsWebService
 */
@Entity
@Table(name = "noisy_word", schema = "webnewsdb")
public class NoisyWordEntity {
    private int id;
    private String word;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "word")
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoisyWordEntity that = (NoisyWordEntity) o;

        if (id != that.id) return false;
        if (word != null ? !word.equals(that.word) : that.word != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (word != null ? word.hashCode() : 0);
        return result;
    }
}
