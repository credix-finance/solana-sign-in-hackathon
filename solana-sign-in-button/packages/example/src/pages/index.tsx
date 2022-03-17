import { Button } from "@components/Button";
import { Input } from "@components/Input";
import { useStore } from "@components/MainMenu";
import { Form } from "antd";
import axios from "axios";
import type { NextPage } from "next";
import { useEffect, useMemo, useState } from "react";

export interface dbPublicKey {
	publicKey: string;
}

export interface User {
	firstName: string;
	lastName: string;
	publicKeys: dbPublicKey[];
}

const Overview: NextPage = () => {
	const [editing, setEditing] = useState<boolean>(false);
	const baseUrl = "http://127.0.0.1:8080";
	const { accessToken, user, setUser } = useStore((state) => ({
		user: state.user,
		setUser: state.setUser,
		accessToken: state.accessToken,
	}));
	const client = useMemo(() => {
		console.log("creating client with token", accessToken);
		return axios.create({
			baseURL: baseUrl,
			headers: { Authorization: accessToken ? `Bearer ${accessToken}` : undefined },
		});
	}, [baseUrl, accessToken]);
	const [form] = Form.useForm();

	useEffect(() => {
		form.setFieldsValue({
			firstName: user?.firstName,
			lastName: user?.lastName,
		});
	}, [user, form]);

	// eslint-disable-next-line @typescript-eslint/no-explicit-any
	const edit = async (values: any) => {
		const formUser = {
			firstName: values.firstName,
			lastName: values.lastName,
		};

		const response = await client.put("/api/users/me", formUser);
		setUser(response.data);
		setEditing(false);
	};

	// eslint-disable-next-line @typescript-eslint/no-explicit-any
	const addKey = async (values: any) => {
		console.log(values);
		await client.post("/api/publickeys", values);
		const response = await client.get("/api/users/me");
		setUser(response.data);
	};

	console.log("user", user);

	return (
		<main
			id="index"
			className="grid grid-cols-1 grid-auto-rows-min gap-y-8 md:grid-cols-12 md:gap-y-12 md:gap-x-14 justify-items-center p-4 pt-8 md:p-8 lg:pt-16 lg:max-w-6xl lg:justify-self-center"
		>
			<div className="text-center md:col-span-12 md:max-w-3xl grid justify-items-center">
				<h1 className="text-5xl md:text-6xl lg:text-7xl font-semibold font-sans">
					Hackerhouse Prague
				</h1>
				<Form onFinish={edit} name="bla" form={form}>
					<Input label="first name" disabled={!editing} name="firstName" />
					<Input label="last name" disabled={!editing} name="lastName" />
					{editing && (
						<Form.Item>
							<Button htmlType="submit" type="primary">
								Save
							</Button>
						</Form.Item>
					)}
					{!editing && <Button onClick={() => setEditing(true)}>Edit</Button>}
				</Form>
				<div className="mt-10">
					{user &&
						user.publicKeys.map((k, i) => (
							<Input label={`key: ${i}`} disabled value={k.publicKey} key={k.publicKey} />
						))}
					<Form onFinish={addKey} name="key" onFinishFailed={console.log}>
						<Input label="add key" value={user?.firstName} name="publicKey" />
						<Form.Item>
							<Button htmlType="submit" type="primary">
								Save
							</Button>
						</Form.Item>
					</Form>
				</div>
			</div>
		</main>
	);
};

export default Overview;
