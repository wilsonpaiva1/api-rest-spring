package br.com.wilson.paiva.apirestspring.unittests.vo.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.xml.bind.annotation.XmlRootElement;


import java.io.Serializable;

@XmlRootElement
public class WrapperPersonVo  implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("_embedded")
    private  PersonEmbeddedVO embedded;

    public WrapperPersonVo() {}

    public PersonEmbeddedVO getEmbedded() {
        return embedded;
    }

    public void setEmbedded(PersonEmbeddedVO embedded) {
        this.embedded = embedded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WrapperPersonVo that)) return false;

        return getEmbedded() != null ? getEmbedded().equals(that.getEmbedded()) : that.getEmbedded() == null;
    }

    @Override
    public int hashCode() {
        return getEmbedded() != null ? getEmbedded().hashCode() : 0;
    }
}
