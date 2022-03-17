import { useWallet } from "@solana/wallet-adapter-react";
import { useWalletModal } from "@solana/wallet-adapter-react-ui";
import { Button } from "antd";
import Icon from "@ant-design/icons";
import axios from "axios";
import base58 from "bs58";
import { useCallback, useEffect, useMemo } from "react";
import SolanaLogo from "./SolanaLogo";
import { WalletButton } from "./WalletButton";

interface Props {
	isLoggedIn: boolean;
	baseUrl: string;
	// eslint-disable-next-line @typescript-eslint/no-explicit-any
	onSignIn: (e?: any) => void;
	// eslint-disable-next-line @typescript-eslint/no-explicit-any
	onSignOut: any;
}

export const SolanaSignInButton = (props: Props) => {
	const { setVisible } = useWalletModal();
	const wallet = useWallet();
	const client = useMemo(
		() => axios.create({ baseURL: props.baseUrl, withCredentials: true }),
		[props.baseUrl]
	);

	const sign = useCallback(async () => {
		const key = wallet.publicKey;

		if (!key) {
			throw new Error("No key associated with the wallet");
		}

		const encoder = new TextEncoder();
		const message = key.toString();

		if (!wallet.signMessage) {
			throw new Error("Unable to sign using this wallet");
		}

		return wallet.signMessage(encoder.encode(message));
	}, [wallet]);

	const logIn = useCallback(async () => {
		if (!wallet.publicKey) {
			throw new Error("No key associated with the wallet");
		}

		const signature = await sign();
		const url = `${props.baseUrl}/login`;
		const payload = {
			username: wallet.publicKey.toString(),
			password: base58.encode(signature),
		};

		try {
			await client.post(url, payload);
			props.onSignIn();
		} catch (e) {
			// TODO: proper error typing + handle redirect
			// props.onSignIn(e);
			props.onSignIn();
		}
	}, [client, sign, wallet.publicKey, props]);

	const click = () => {
		if (!wallet.connected) {
			setVisible(true);
			return;
		}

		if (!props.isLoggedIn) {
			logIn();
		}
	};

	useEffect(() => {
		if (wallet.connected && !wallet.connecting && !props.isLoggedIn) {
			logIn();
		}
	}, [wallet.connected, wallet.connecting, props.isLoggedIn, logIn]);

	if (props.isLoggedIn && wallet.connected) {
		return <WalletButton onDisconnect={props.onSignOut} />;
	}

	return (
		<Button
			size="large"
			onClick={click}
			type="primary"
			icon={<Icon component={SolanaLogo} />}
			style={{
				backgroundColor: "white",
				border: "1px solid #9945FF",
				color: "black",
				borderRadius: "12px",
			}}
		>
			Sign in with Solana
		</Button>
	);
};
