package net.kernal.spiderman.worker.extract.schema.filter;

import net.kernal.spiderman.worker.extract.schema.Field.ValueFilter;

public class TrimFilter implements ValueFilter {

	@Override
	public String filter(Context ctx) {
		return ctx.getValue().trim();
	}

}
