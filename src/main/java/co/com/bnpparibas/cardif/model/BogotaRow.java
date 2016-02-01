package co.com.bnpparibas.cardif.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "BOGOTAROW")
@NamedQueries({ 
	@NamedQuery(name = "@ROWS_BOGOTA", 
            query = "FROM BogotaRow as br WHERE br.saveDate BETWEEN :stDate AND :edDate ") })
public class BogotaRow implements Serializable {

	private static final long serialVersionUID = 1L;
	

	@Id
	@Column(name = "ID_ROW")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idRow;
	
	
	@Column(name = "CERTIFICATE_NUMBER")
	private String certNumber;	

	@Column(name = "PRODUCT_ID")
	private String prodId;

	@Column(name = "INSURED_NAME")
	private String name;

	@Column(name = "EFFECTIVE_DATE")
	private Date initDate;

	@Column(name = "EXPIRATION_DATE")
	private Date finalDate;

	@Column(name = "PERSONAL_ID")
	private Integer id;
	
	@Column(name = "SAVE_DATE")
	private Date saveDate;	
	

	public Integer getIdRow() {
		return idRow;
	}

	public void setIdRow(Integer idRow) {
		this.idRow = idRow;
	}

	public String getCertNumber() {
		return certNumber;
	}

	public void setCertNumber(String certNumber) {
		this.certNumber = certNumber;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getInitDate() {
		return initDate;
	}

	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}

	public Date getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}

	
	

}
