import useApi from "./useApi";

export interface SignInDto {
	name: string;
	email: string;
	dni: string;
	password: string;
}

export interface LogInDTO {
	email: string;
	password: string;
}

export default function () {
	const signin = (body: SignInDto) => {
		return useApi(`/user`, {
			body,
			method: 'POST',
		});
	};

    const login = (body: LogInDTO) => {
		return useApi(`/user/login`, {
			body,
			method: 'POST',
		});
	};

	return {
		signin,
        login,
	};
}
