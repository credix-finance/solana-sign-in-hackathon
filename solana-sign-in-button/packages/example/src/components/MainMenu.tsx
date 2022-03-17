import { CredixLogo } from "@components/CredixLogo";
import axios from "axios";
import { User } from "pages";
import React, { useCallback, useEffect, useMemo, useState } from "react";
import create from "zustand";
import { SolanaSignInButton } from "solana-sign-in-button";

type Store = {
	user: User;
	// eslint-disable-next-line @typescript-eslint/no-explicit-any
	setUser: (u: User) => void;
	accessToken: string;
	setAccessToken: (t: string) => void;
	clear: () => void;
};

export const useStore = create<Store>((set) => ({
	user: null,
	setUser: (user: User) => set((state) => ({ ...state, user: user })),
	accessToken: null,
	setAccessToken: (t: string) => set((state) => ({ ...state, accessToken: t })),
	clear: () =>
		set((state) => ({
			setUser: state.setUser,
			setAccessToken: state.setAccessToken,
			clear: state.clear,
			user: null,
			accessToken: null,
		})),
}));

export const MainMenu = () => {
	const [loggedIn, setLoggedIn] = useState<boolean>(false);
	const [started, setStarted] = useState<boolean>(false);
	const baseUrl = "http://127.0.0.1:8080";

	const { setAccessToken, accessToken, setUser } = useStore((state) => ({
		setUser: state.setUser,
		setAccessToken: state.setAccessToken,
		accessToken: state.accessToken,
	}));

	const client = useMemo(() => {
		console.log("creating client with token", accessToken);
		return axios.create({
			baseURL: baseUrl,
			headers: { Authorization: accessToken ? `Bearer ${accessToken}` : undefined },
		});
	}, [baseUrl, accessToken]);

	const getMe = useCallback(async () => {
		console.log("getting me");
		try {
			const response = await client.get("/api/users/me");
			setLoggedIn(true);
			setUser(response.data);
		} catch (e) {
			if (e.response && e.response.status === 401) {
				setLoggedIn(false);
				console.log("not logged in");
			}
		}
	}, [client, setUser]);

	const start = useCallback(async () => {
		await getMe();
		setStarted(true);
		console.log("started");
	}, [getMe]);

	useEffect(() => {
		if (!started) {
			console.log("starting");
			start();
		}
	}, [started, start]);

	const callback = (e, r) => {
		console.log(r);
		if (r) {
			client.defaults.headers["Authorization"] = `Bearer ${r.data.access_token}`;
			setLoggedIn(true);
			getMe();
			setAccessToken(r.data.access_token);
			console.log("logged in");
		}
	};

	return (
		<div className="w-full bg-neutral-0 flex justify-between items-center py-[16.5px] px-4">
			<div>
				<CredixLogo />
			</div>
			<div className="flex space-x-4">
				<SolanaSignInButton
					baseUrl="http://127.0.0.1:8080"
					isLoggedIn={loggedIn}
					onSignIn={callback}
					onSignOut={() => {
						console.log("sign out");
						setLoggedIn(false);
					}}
				/>
			</div>
		</div>
	);
};
