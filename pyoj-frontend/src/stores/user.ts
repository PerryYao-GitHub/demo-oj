import { defineStore } from "pinia";
import type { UserVO } from "../types/user";
import request from "../axios";
import type { AppResponse } from "../types/global";

export const useUserStore = defineStore('user', {
    state: () => ({
        user: null as UserVO | null,
    }),

    actions: {
        setUser(user: UserVO) {
            this.user = user;
        },

        async clearUser() {
            try {
                await request.post('/logout');
            } catch (error) {
                console.error("Failed to log out:", error);
                alert("Logout failed. Please try again.");
            }
            this.user = null;
        },

        isLogin(): boolean {
            return this.user !== null;
        },

        isAdmin(): boolean {
            return this.user !== null && this.user.role === 'admin';
        },

        async fetchCurrentUser() {
            try {
                const response = await request.get<AppResponse<UserVO>>('/user');
                if (response.data.code === 0) {
                    this.setUser(response.data.data);
                }
            } catch (error) {}
        }
    }
});