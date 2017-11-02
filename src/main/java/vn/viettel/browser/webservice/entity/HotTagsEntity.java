package vn.viettel.browser.webservice.entity;

import javax.persistence.*;

/**
 * Created by quytx on 31/10/17.
 * vn.viettel.browser.webservice.dao : NewsWebService
 */
@Entity
@Table(name = "hot_tags", schema = "webnewsdb")
public class HotTagsEntity {
    private int id;
    private Integer order;
    private Integer idCategory;
    private String tag;
    private String displayName;
    private Integer frequency;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "order")
    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Basic
    @Column(name = "id_category")
    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }

    @Basic
    @Column(name = "tag")
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Basic
    @Column(name = "display_name")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Basic
    @Column(name = "frequency")
    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HotTagsEntity that = (HotTagsEntity) o;

        if (id != that.id) return false;
        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        if (idCategory != null ? !idCategory.equals(that.idCategory) : that.idCategory != null) return false;
        if (tag != null ? !tag.equals(that.tag) : that.tag != null) return false;
        if (displayName != null ? !displayName.equals(that.displayName) : that.displayName != null) return false;
        if (frequency != null ? !frequency.equals(that.frequency) : that.frequency != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (idCategory != null ? idCategory.hashCode() : 0);
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (frequency != null ? frequency.hashCode() : 0);
        return result;
    }
}
