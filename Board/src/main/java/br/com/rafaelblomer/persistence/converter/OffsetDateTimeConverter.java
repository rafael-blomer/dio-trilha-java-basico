package br.com.rafaelblomer.persistence.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

import java.sql.Timestamp;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OffsetDateTimeConverter {

	public static OffsetDateTime toOffsetDateTime(final Timestamp value) {
		return Objects.nonNull(value) ? OffsetDateTime.ofInstant(value.toInstant(), ZoneOffset.UTC) : null;
	}
	
	public static Timestamp toTimeStamp(final OffsetDateTime value) {
		return Objects.nonNull(value) ? Timestamp.valueOf(value.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()) : null;
	}
}
