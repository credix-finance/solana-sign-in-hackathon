import axios, { AxiosError } from "axios";
import { dbPublicKey, User } from "state";

export const baseURL = "http://127.0.0.1";
export const bffClient = axios.create({
	baseURL: `${baseURL}:8082`,
	withCredentials: true,
});
export const backendClient = axios.create({
	baseURL: `${baseURL}:8081`,
	withCredentials: true
});

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export const createUser = (errorHandler: any) => {
	try {
		return backendClient.post("/api/users", {});
	} catch (e) {
		errorHandler(e);
	}
};

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export const getUser = async (errorHandler: any) => {
	try {
		const response = await backendClient.get("/api/users/me");
		return response.data;
	} catch (e) {
		errorHandler(e);
	}
};

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export const updateUser = async (user: Partial<User>, errorHandler: any) => {
	try {
		const response = await backendClient.put("/api/users/me", user);
		return response.data;
	} catch (e) {
		errorHandler(e);
	}
};

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export const addPublickey = async (key: dbPublicKey, errorHandler: any) => {
	try {
		const response = await backendClient.post("/api/publickeys", {
			publicKey: key.publicKey.toLowerCase()
		});
		return response.data;
	} catch (e) {
		errorHandler(e);
	}
};

export const errorHandler = (onUnauthorized: () => void) => (e: Error | AxiosError) => {
	if (axios.isAxiosError(e) && e.response && e.response.status === 401) {
		onUnauthorized();
	}

	// TODO: throw exception
	console.log(e);
	// throw e;
};
