import { defineStore } from "pinia";
import type { UserVO } from "../types/user";
import axios from "axios";
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
                await axios.post('/api/logout');
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
                const response = await axios.get<AppResponse<UserVO>>('/api/user');
                if (response.data.code !== 0) {
                    throw new Error(response.data.message);
                }
                this.setUser(response.data.data);
            } catch (error) {
                console.error("Failed to fetch user data:", error);
                alert("Failed to fetch user data. Please log in again.");
                this.clearUser();
            }
        }
    }
});