package curs.library.model.pojo;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import curs.library.service.helper.Rounder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Simple Java Bean object that represents a bill that a {@link User}
 * will pay if his/her {@link Request} for a {@link Book} has been approved
 * by Admin.
 * If the {@link User} has paid the bill, the boolean 'isPaid' would be set to 'TRUE'.
 */


@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bills")
public class Bill implements Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Getter 		private Long             	id;

	@Getter 		private double				sum;
	@Getter @Setter private boolean             isPaid;
	@Getter @Setter private Long             userId;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "request_id", referencedColumnName = "id")
	@Getter 		private Request				request;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "book_id", referencedColumnName = "id")
	@Getter			private Book book;

	public Bill(double sum, boolean isPaid, @NonNull Request request,
				@NonNull Book book, long userid) {
		setSum(sum);
		this.isPaid = isPaid;
		this.request = request;
		this.book = book;
		this.userId=userid;
		log.info("Object Bill successfully created");
	}

	public void setSum(double sum) {
			this.sum = Rounder.round(sum);
			log.info("Double value of 'sum' field set succesfully: " + this.sum);
	}

	public void setId(@NonNull Long id) {
		this.id = id;
	}

	public void setRequest(@NonNull Request request) {
		this.request = request;
	}

	public void setBook(@NonNull Book book) {
		this.book = book;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Bill bill = (Bill) o;
		return id.equals(bill.id) &&
				Double.compare(bill.sum, sum) == 0 &&
				isPaid == bill.isPaid &&
				request.equals(bill.request) &&
				book.equals(bill.book);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, sum, isPaid, request, book);
	}
}