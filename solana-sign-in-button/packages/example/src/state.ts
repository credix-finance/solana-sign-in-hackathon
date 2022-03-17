import create from "zustand";

export interface dbPublicKey {
	publicKey: string;
}

export type User = {
	firstName: string;
	lastName: string;
	publicKeys: dbPublicKey[];
};

export type UserStore = {
	user: User | null;
	setUser: (user: User) => void;
};

export type AuthStore = {
	isLoggedIn: boolean;
	setLoggedIn: (isLoggedIn: boolean) => void;
};

export const useUserStore = create<UserStore>((set) => ({
	user: null,
	setUser: (user: User) => set((state) => ({ ...state, user: user })),
}));

export const useAuthStore = create<AuthStore>((set) => ({
	isLoggedIn: false,
	setLoggedIn: (isLoggedIn: boolean) => set((state) => ({ ...state, isLoggedIn })),
}));
