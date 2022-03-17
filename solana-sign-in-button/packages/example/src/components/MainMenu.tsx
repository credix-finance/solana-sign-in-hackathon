import { CredixLogo } from "@components/CredixLogo";
import { baseURL, createUser, errorHandler, getUser } from "client";
import React, { useCallback, useEffect, useState } from "react";
import { useAuthStore, useUserStore } from "state";
import { SolanaSignInButton } from "./SolanaSignIn/SolanaSignInButton";

export const MainMenu = () => {
	const { isLoggedIn, setLoggedIn } = useAuthStore((state) => ({
		isLoggedIn: state.isLoggedIn,
		setLoggedIn: state.setLoggedIn,
	}));
	const { setUser } = useUserStore((state) => ({ setUser: state.setUser }));

	const [started, setStarted] = useState<boolean>(false);

	const start = useCallback(async () => {
		const user = await getUser(errorHandler(() => setLoggedIn(false)));
		setUser(user);
		setStarted(true);
	}, [setLoggedIn, setUser]);

	useEffect(() => {
		if (!started) {
			start();
		}
	}, [started, start]);

	const onSignIn = async (e) => {
		if (e) {
			console.log(e);
			return;
		}
		setLoggedIn(true);
		await createUser(errorHandler(() => setLoggedIn(false)));
		const user = await getUser(errorHandler(() => setLoggedIn(false)));
		setUser(user);
	};

	return (
		<div className="w-full bg-neutral-0 flex justify-between items-center py-[16.5px] px-4">
			<div>
				<CredixLogo />
			</div>
			<div className="flex space-x-4">
				<SolanaSignInButton
					baseUrl={`${baseURL}:8082`}
					isLoggedIn={isLoggedIn}
					onSignIn={onSignIn}
					onSignOut={() => {
						console.log("sign out");
						setLoggedIn(false);
					}}
				/>
			</div>
		</div>
	);
};
