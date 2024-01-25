import { UseFetchOptions } from 'nuxt/app';

export interface ExceptionError {
	code: string;
	message:
		| {
				en: string;
				es: string;
				cat: string;
		  }
		| string[];
}
export default async function <T>(url: string, options: UseFetchOptions<T> = {}) {
	const runtime = useRuntimeConfig();

	const ret = await useFetch(url, {
		baseURL: `http://localhost:8080`,
		...options,
	});

	if (ret.error.value) {
		throw ret.error.value.data;
	}

	return ret;
}