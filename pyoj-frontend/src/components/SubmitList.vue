<template>
  <div class="submit-list">
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Question</th>
          <th>Status</th>
          <th>Language</th>
          <th>Time</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="submit in submits"
          :key="submit.id"
          @click="showDetails(submit)"
          class="submit-item"
        >
          <td>{{ submit.id }}</td>
          <td>{{ submit.questionTitle }}</td>
          <td>{{ submit.status }}</td>
          <td>{{ submit.lang }}</td>
          <td>{{ submit.createTime }}</td>
        </tr>
      </tbody>
    </table>

    <!-- 弹窗展示详细信息 -->
    <div v-if="selectedSubmit" class="modal">
      <div class="modal-content">
        <h2>Submission Details</h2>
        <p><strong>ID:</strong> {{ selectedSubmit.id }}</p>
        <p><strong>Question:</strong> {{ selectedSubmit.questionTitle }}</p>
        <p><strong>Status:</strong> {{ selectedSubmit.status }}</p>
        <p><strong>Language:</strong> {{ selectedSubmit.lang }}</p>
        <p><strong>Time:</strong> {{ selectedSubmit.createTime }}</p>
        <p><strong>Code:</strong></p>
        <pre class="code">{{ selectedSubmit.code }}</pre>
        <p><strong>Judge Result:</strong></p>
        <ul>
          <li><strong>Message:</strong> {{ selectedSubmit.judgeResult.message }}</li>
          <li><strong>Status:</strong> {{ selectedSubmit.judgeResult.status }}</li>
          <li><strong>Memory:</strong> {{ selectedSubmit.judgeResult.memory }} KB</li>
          <li><strong>Time:</strong> {{ selectedSubmit.judgeResult.time }} ms</li>
        </ul>
        <button @click="closeDetails" class="close-button">Close</button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import type { SubmitVO } from '../types/submit'

export default defineComponent({
  name: 'SubmitList',
  props: {
    submits: {
      type: Array as () => SubmitVO[],
      required: true
    }
  },
  setup() {
    const selectedSubmit = ref<SubmitVO | null>(null)

    const showDetails = (submit: SubmitVO) => {
      selectedSubmit.value = submit
    }

    const closeDetails = () => {
      selectedSubmit.value = null
    }

    return {
      selectedSubmit,
      showDetails,
      closeDetails
    }
  }
})
</script>

<style scoped>
.submit-list table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 1rem;
}

.submit-list th,
.submit-list td {
  border: 1px solid #ddd;
  padding: 0.5rem;
  text-align: left;
}

.submit-list th {
  background-color: #f4f4f4;
}

.submit-item {
  cursor: pointer;
}

.submit-item:hover {
  background-color: #f9f9f9;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background-color: white;
  padding: 2rem;
  border-radius: 8px;
  width: 80%;
  max-width: 600px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.code {
  background-color: #f4f4f4;
  padding: 1rem;
  border-radius: 4px;
  overflow-x: auto;
  white-space: pre-wrap;
}

.close-button {
  margin-top: 1rem;
  padding: 0.5rem 1rem;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.close-button:hover {
  background-color: #0056b3;
}
</style>